# new_clipper

#### 介绍
adb控件剪切板文字

#### 命令

- 更改剪切板文字：`adb shell am start-foreground-service -n wenjie.star.new_clipper/.ClipboardService -a set --es type text --es text \"你好  bytedancer\"`  
> '\'如果文字内容包含空格之类的字符，则需要斜杠

