发送模拟事件到Fluentd
```bash
java -jar **.jar --host=192.168.148.135 --port=24224 --times=10  --tag=unomi.event.test --i=1000 --thread=2 --sid=xxx
```