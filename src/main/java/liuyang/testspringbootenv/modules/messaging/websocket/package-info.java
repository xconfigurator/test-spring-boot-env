/**
 * 视频教程：
 * https://www.bilibili.com/video/BV1sW411r7Tz/?p=1&vd_source=8bd7b24b38e3e12c558d839b352b32f4 （20221108 服务端的视频看过很多，这个是目前找到最好的。）
 *
 * 参考文档：
 * https://docs.spring.io/spring-boot/docs/current/reference/html/messaging.html#messaging.websockets
 * https://docs.spring.io/spring-framework/docs/5.3.21/reference/html/web.html#websocket
 * Guides:
 * https://spring.io/guides/gs/messaging-stomp-websocket/
 *
 *
 * Nginx代理 1.3+
 * 视频：https://www.bilibili.com/video/BV1sW411r7Tz?p=20&vd_source=8bd7b24b38e3e12c558d839b352b32f4
 * 摘要：
 * proxy_set_header Upgrade $http_upgrade;
 * proxy_set_header Connection "upgrade";
 * ip_hash;
 *
 * http {
 *     map $http_upgrade $connection_upgrade {
 *         default upgrade;
 *         '' close;
 *     }
 *
 *     upstream websocket {
 *         ip_hash;
 *         server localhost:3100;
 *         server localhost:3101;
 *     }
 *
 *     server {
 *         listen 8020;
 *         location / {
 *             proxy_pass http://websocket;
 *             proxy_http_version 1.1;
 *             proxy_set_header Upgrade $http_upgrade;
 *             proxy_set_header Connection $connection_upgrade;
 *         }
 *     }
 * }
 *
 * @author liuyang(wx)
 * @since 2022/7/11
 */
package liuyang.testspringbootenv.modules.messaging.websocket;