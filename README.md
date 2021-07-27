# new_clipper

#### 介绍
adb控件剪切板文字

#### 命令

- 对于禁止后台启动service的手机，要执行：`adb shell am start wenjie.star.new_clipper/.MainActivity`

- 更改剪切板文字：`adb shell am start-foreground-service -n wenjie.star.new_clipper/.ClipboardService -a set --es type text --es text \"你好  bytedancer\"`  
> '\'如果文字内容包含空格之类的字符，则需要斜杠
> 如果出现`Error: Not found; no service started`，那就是手机禁止后台了，先运行第一条启动activity的命令后马上重试

