```javascript
<template>
  <div>
    <el-button @click="doSubscription">订阅JVM信息</el-button>
    <span>{{ jvminfo }}</span>
  </div>
</template>

<script>
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';

export default {
  data () {
    return {
      jvminfo: ''
    }
  },
  methods: {
    doSubscription () {
      console.log(123);
      var outer =  this;
      //connect();
      var socket = new SockJS('http://localhost/gs-guide-websocket-endpoint');
      var stompClient = Stomp.over(socket);
      stompClient.connect({}, function (frame) {
          //setConnected(true);
          console.log('Connected: ' + frame);
          stompClient.subscribe('/topic/jvminfo', function (greeting) {
              //console.log("123123 :: " + greeting);
              console.log(JSON.parse(greeting.body).content);
              //showGreeting(JSON.parse(greeting.body).content);
              outer.jvminfo = greeting;
          });
      });

      sleep(2000);
      
      // controller的请求地址
      stompClient.send("/ws/jvminfo", {}, {});
    }
    /*
    setConnected(connected) {
      $("#connect").prop("disabled", connected);
      $("#disconnect").prop("disabled", !connected);
      if (connected) {
          $("#conversation").show();
      }
      else {
          $("#conversation").hide();
      }
      $("#greetings").html("");
    },
    */
   /*
    connect() {
        var socket = new SockJS('http://localhost/gs-guide-websocket-endpoint');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            //setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('http://localhost/ws/topic/jvminfo', function (greeting) {
                //showGreeting(JSON.parse(greeting.body).content);
            });
        });
    },
    */
   /*
    disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        //setConnected(false);
        console.log("Disconnected");
    },
    */
   /*
    sendName() {
        // controller的请求地址
        stompClient.send("/ws/jvminfo", {}, {});
    }
    */
  },
  mounted () {
    // TODO 这里可以执行订阅操作

  }
}
</script>

<style>

</style>
```