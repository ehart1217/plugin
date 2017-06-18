# plugin
这一个可以轻量级加载第三方android app的插件方案。

适用场景：插件和宿主是不同团队开发；插件的首页需要嵌入到宿主中。

优势：
- 第三方app只需要提供包名，主页资源文件名称给宿主。耦合低。
- 已成熟的第三方app只需要把需要展示的页面重构成一个自定义View。
- 第三方app感知不到自己是插件，开发流程和正常app完全一致。
- 宿主集成非常方便。

详情参照：[android加载已安装app的代码作为插件](http://www.jianshu.com/p/b0bdc6925171)
