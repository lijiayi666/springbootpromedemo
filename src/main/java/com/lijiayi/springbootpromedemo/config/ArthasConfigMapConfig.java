//package com.lijiayi.springbootpromedemo.config;
//
//import com.alibaba.arthas.spring.ArthasProperties;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.util.SocketUtils;
//import org.springframework.util.StringUtils;
//
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.security.SecureRandom;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
///**
// * @Author: niuzhenyu
// * @Description:
// * @Date:Create：in 2022/6/28 15:45
// * @Modified By：
// */
//@Slf4j
//@Configuration
//@ConditionalOnClass(value = ArthasProperties.class)
//public class ArthasConfigMapConfig {
//
//    @Value("${zhxy.service.microServiceName}")
//    private String serviceName;
//
//    @Value("${server.port:8080}")
//    private String serverPort;
//
//    @Autowired
//    private Environment environment;
//
//    @Bean("arthasConfigMap")
//    public Map<String, String> arthasConfigMap(@Autowired ArthasProperties arthasProperties) {
//        Map<String, String> map = new HashMap<>();
//
//        //接收环境变量参数，否则随机生成
//        String agentId = environment.getProperty("arthas.agentId");
//        //优先使用环境变量arthas.name，其次使用服务名+随机数
//        if (agentId == null) {
//            Random secureRandom = new SecureRandom();
//            agentId = serviceName + ":" + secureRandom.nextInt(1000);
//        }
//        log.info("agentId:{}", agentId);
//        map.put("agent-id", agentId);
//
//
////        map.put("tunnel-server", tunnelServer);
//
//        Integer serverPortInt = null;
//        if(!StringUtils.isEmpty(serverPort)) {
//            serverPortInt = Integer.parseInt(serverPort);
//        }
//
//        //优先从环境变量中获取，其次从配置文件中获取，否则为serverPort+1，否则随机（值为0），若值为-1则不监听端口
//        String httpPort = environment.getProperty("arthas.httpPort");
//        if (httpPort == null) {
//            if (arthasProperties.getHttpPort() == 0) {
//                if (serverPortInt!=null) {
//                    map.put("http-port", "" + (serverPortInt + 1));
//                } else {
//                    map.put("http-port", "" + SocketUtils.findAvailableTcpPort());
//                }
//            } else {
//                map.put("http-port", "" + arthasProperties.getHttpPort());
//            }
//        } else {
//            map.put("http-port", httpPort);
//        }
//        log.info("httpPort:{}", map.get("http-port"));
//
//
//        //优先从环境变量中获取，其次从配置文件中获取，否则为serverPort+2，否则随机（值为0），若值为-1则不监听端口
//        String telnetPort = environment.getProperty("arthas.telnetPort");
//
//        if (telnetPort == null) {
//            if (arthasProperties.getTelnetPort() == 0) {
//                if (serverPortInt!=null) {
//                    map.put("telnet-port", "" + (serverPortInt + 2));
//                } else {
//                    map.put("telnet-port", "" + SocketUtils.findAvailableTcpPort());
//                }
//            } else {
//                map.put("telnet-port", "" + arthasProperties.getHttpPort());
//            }
//        } else {
//            map.put("telnet-port", telnetPort);
//        }
//        log.info("telnetPort:{}", map.get("telnet-port"));
//
//
//        //如果有配置target-ip，那么使用，否则默认127.0.0.1
//        if (!StringUtils.isEmpty(arthasProperties.getIp())) {
//            map.put("ip", arthasProperties.getIp());
//        }
//        log.debug("targetIp:{}", map.get("ip"));
//
//        //不禁用stop命令
//        map.put("disabledCommands", "");
//        String ip = null;
//        try {
//            ip = InetAddress.getLocalHost().getHostAddress();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        String port = environment.getProperty("server.port");
//        agentId = ip + ":" + port;
//        System.out.println(agentId);
//        map.put("agent-id", agentId);
//        map.put("disabled-commands", "dashboard,stop");
//        return map;
//    }
//}
