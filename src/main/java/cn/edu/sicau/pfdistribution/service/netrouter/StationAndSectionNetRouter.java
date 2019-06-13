package cn.edu.sicau.pfdistribution.service.netrouter;

import NetRouterClient.Address;
import NetRouterClient.NetRouterClient;
import NetRouterClient.RecvMessage;
import NetRouterClient.SendMessage;
import cn.edu.sicau.pfdistribution.service.kspdistribution.MainDistribution;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/*
测试用例内容：
向目的客户端发送的数据，收到回复后发送下一包
*/
@Component
@Service
public class StationAndSectionNetRouter {

    Logger log = LoggerFactory.getLogger(StationAndSectionNetRouter.class);

    private Gson gson = new GsonBuilder().create();
    @Autowired
    private MainDistribution distribution;


    private synchronized static void loadJNILibDynamically(String libName) throws IOException {
        String systemType = System.getProperty("os.name");
        String libExtension = (systemType.toLowerCase().indexOf("win")!=-1) ? ".dll" : ".so";

        String libFullName = libName + libExtension;

        String nativeTempDir = System.getProperty("java.io.tmpdir");

        InputStream in = null;
        BufferedInputStream reader = null;
        FileOutputStream writer = null;

        File extractedLibFile = new File(nativeTempDir+File.separator+libFullName);
        if(!extractedLibFile.exists()){
            try {
                in = StationAndSectionNetRouter.class.getResourceAsStream(".\\resource\\"+libFullName);
                if(in==null)
                    in =  StationAndSectionNetRouter.class.getResourceAsStream(libFullName);
                StationAndSectionNetRouter.class.getResource(libFullName);
                reader = new BufferedInputStream(in);
                writer = new FileOutputStream(extractedLibFile);

                byte[] buffer = new byte[1024];

                while (reader.read(buffer) > 0){
                    writer.write(buffer);
                    buffer = new byte[1024];
                }
            } catch (IOException e){
                e.printStackTrace();
            } finally {
                if(in!=null)
                    in.close();
                if(writer!=null)
                    writer.close();
            }
        }
        System.load(extractedLibFile.toString());
    }

    private boolean SendData(NetRouterClient netClient, List<Address> f_list, String data) {
//        gson.toJson(data)
        SendMessage f_msg = new SendMessage(f_list, data);
        if (!netClient.sendMessage(f_msg)) {
            System.out.println("Send fail");
            return false;
        }
        System.out.println("Station and section Send suc");
        return true;
    }

    @Async
    public void receiver() throws Exception {
        loadJNILibDynamically("NetRouterCppClient");
        Address localaddr = new Address((byte) 8, (byte) 1, (short) 2, (byte) 2, (short) 6);
        List<Address> destAddrs = new LinkedList<Address>();
        Address destaddr1 = new Address((byte) 8, (byte) 1, (short) 4, (byte) 1, (short) 6);
        destAddrs.add(destaddr1);
//注册信息
/*        String reginfo =
                "<in_condition>\n"+
                        "<rec>\n"+
                        "<protocol418_condition>\n"+
                        "<type_func>0x04,0x09</type_func>\n"+
                        "<type_func>0x04,0x09</type_func>\n"+
                        "</protocol418_condition>\n"+
                        "</rec>\n"+
                        "</in_condition>\n";*/

        NetRouterClient netRouterClient = new NetRouterClient("Test", "10.0.140.213", 9003, "10.2.55.51", 9005, localaddr, "");
        while (!netRouterClient.start()) {
            System.out.println("StationAndSectionNetRouter  Start fails.");
            Thread.sleep(10);
        }
        System.out.println("StationAndSectionNetRouter Start succeeds.");

//        SendData(netRouterClient, destAddrs,args);

        while (true) {
            if (netRouterClient.isNet1Connected() || netRouterClient.isNet2Connected()) {
                RecvMessage recvMessage = new RecvMessage();
                if (netRouterClient.receiveBlockMessage(recvMessage)) {
                    String a = recvMessage.getMessage();
                    log.info("从NetRouter读取数据" );
                    try {
                        if (recvMessage != null) {
                            JsonTransfer jsonTransfer = new JsonTransfer();
                            Boolean data = jsonTransfer.stationDataAnalysis(recvMessage.getMessage());
                            log.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                            log.info("数据处理正常" );
                            Map<String, String> message = new HashMap<>();
                            message.put("command", "static");
                            message.put("startTime", "2018-09-01 09:00:19");
                            message.put("timeInterval", "60");
                            message.put("predictionInterval", "15");
                            final List<Tuple2<String, String>> list = new java.util.ArrayList<>(message.size());
                            for (final Map.Entry<String, String> entry : message.entrySet()) {
                                list.add(Tuple2.apply(entry.getKey(), entry.getValue()));
                            }
                            final scala.collection.Seq<Tuple2<String, String>> seq = scala.collection.JavaConverters.asScalaBufferConverter(list).asScala().toSeq();
                            scala.collection.immutable.Map<String, String> abc = (scala.collection.immutable.Map<String, String>) scala.collection.immutable.Map$.MODULE$.apply(seq);
                            /*distribution.triggerTask(abc);*/
                            String back = "{'time':'2019/5/30 15:54:00','staion_distribution':[{'path':'三亚湾-2-民心佳园-2-重庆北站北广场-2-重庆北站南广场-o-重庆北站南广场-2-龙头寺公园-2-红土地','passengers':'2'},{'path':'空港广场-2-双凤桥-2-碧津-2-双龙-2-回兴-2-长福路-2-翠云-2-园博园-2-鸳鸯-2-金童路-2-金渝','passengers':'5'}]}";
                            log.info("数据接受成功" + back);
                            SendData(netRouterClient, destAddrs, back);
                        }
                    }catch (Exception e) {
                        System.out.println("数据不对应");
                    }
                }
            }
        }
    }
}
