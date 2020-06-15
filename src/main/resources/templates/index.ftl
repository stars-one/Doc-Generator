<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
    <meta content="text/html; charset=utf-8">
    <title>${title}</title>
    <#--<script src="js/vue.js" type="text/javascript" charset="utf-8"></script>-->
    <script src="js/mdui.js" type="text/javascript" charset="utf-8"></script>
    <script>
        var $$ = mdui.JQ;
        //修改左侧导航条选中背景
        function itemActive(m){
            $$('a').filter('.mdui-list-item',".mudi-ripper").each(function(i,ele){
                if(i==m){
                    $$(ele).addClass("mdui-list-item-active");
                }else{
                    $$(ele).removeClass("mdui-list-item-active");
                }
            });
        }
        function load(n) {
            itemActive(n);
        <#list partDataList as partData >
           <#list partData.chapterDataList as chapterData >
            if (n==${chapterData.index}){
               $$("#contentIframe").attr("src", "${chapterData.filePath}");
            }
            </#list>
        </#list>
        }
    </script>
    <link rel="stylesheet" href="css/mdui.css">
</head>

<body class="mdui-drawer-body-left mdui-appbar-with-toolbar mdui-theme-primary-blue mdui-theme-accent-pink">
<header class="mdui-appbar mdui-appbar-fixed">
    <div class="mdui-toolbar mdui-color-theme">
				<span class="mdui-btn mdui-btn-icon mdui-ripple mdui-ripple-white"
                      mdui-drawer="{target: '#main-drawer', swipe: true}"><i
                        class="mdui-icon material-icons">menu</i></span>
        <a href="./" class="mdui-typo-title">${title}</a>
        <div class="mdui-toolbar-spacer"></div>
        <#--Gitee-->
        <#if giteeUrl!="">
        <a href="${giteeUrl}" target="_blank" class="mdui-ripple mdui-ripple-white mdui-valign" mdui-tooltip="{content: '查看 Gitee'}">
            <img width="24px" height="24px" src="img/gitee.png" />
        </a>
        </#if>
        <#--Github-->
        <#if githubUrl!="">
        <a href="${githubUrl}" target="_blank" class="mdui-ripple mdui-ripple-white mdui-valign" mdui-tooltip="{content: '查看 Github'}">
            <img width="24px" height="24px" src="img/github.png" />
        </a>
        </#if>
    </div>
</header>

<div class="mdui-drawer" id="main-drawer">
    <div class="mdui-list" mdui-collapse="{accordion: true}">
    <#list partDataList as partData >
        <#--每个部分-->
         <div class="mdui-collapse-item <#if partData.index==0>mdui-collapse-item-open</#if>">
             <#--列表标题-->
              <div class="mdui-collapse-item-header mdui-list-item mdui-ripple">
                 <i class="mdui-list-item-icon mdui-icon material-icons mdui-text-color-blue">book</i>
                 <div class="mdui-list-item-content">${partData.title}</div>
                 <i class="mdui-collapse-item-arrow mdui-icon material-icons">keyboard_arrow_down</i>
              </div>
             <#--每部分下面的列表内容-->
             <div class="mdui-collapse-item-body mdui-list">
             <#list partData.chapterDataList as chapterData >
                <a onclick="load(${chapterData.index})" class="mdui-list-item mdui-ripple <#if chapterData.index==0>mdui-list-item-active</#if>">${chapterData.title}</a>
             </#list>
             </div>
        </div>
    </#list>
    </div>

</div>

<div class="mdui-m-l-1">
    <iframe id="contentIframe" src="${partDataList[0].chapterDataList[0].filePath}"
            style="border: medium none;width: 100%;height: 100%;"></iframe>
</div>
<div class="doc-footer-nav-text">
    <p class="mdui-text-center"><strong>本篇文档由<a target="_blank" href="https://github.com/Stars-One/Doc-Generator">文档生成器</a></strong>生成</p>
</div>
</body>
</html>
