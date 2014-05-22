function NiftyCheck(){
if(!document.getElementById || !document.createElement)
    return(false);
var b=navigator.userAgent.toLowerCase();
if(b.indexOf("msie 5")>0 && b.indexOf("opera")==-1)
    return(false);
return(true);
}

function Rounded(selector,bk,color,size,stprefix){
var i;
var v=getElementsBySelector(selector);
var l=v.length;
if(!stprefix){
  stprefix="r";
}
for(i=0;i<l;i++){
    AddTop(v[i],bk,color,size,stprefix);
    AddBottom(v[i],bk,color,size,stprefix);
    }
}

function RoundedTop(selector,bk,color,size,stprefix){
var i;
var v=getElementsBySelector(selector);
if(!stprefix){
  stprefix="r";
}
for(i=0;i<v.length;i++)
    AddTop(v[i],bk,color,size);
}
function RoundedBorderTop(selector,bk,color,size,border,stprefix){
var i;
var v=getElementsBySelector(selector);
if(!stprefix){
  stprefix="r";
}

    for(i=0;i<v.length;i++)
    AddBorderTop(v[i],bk,color,size,border,stprefix);
}

function RoundedBottom(selector,bk,color,size,stprefix){
var i;
var v=getElementsBySelector(selector);
if(!stprefix){
  stprefix="r";
}
for(i=0;i<v.length;i++)
    AddBottom(v[i],bk,color,size,stprefix);
}

function RoundedBorderBottom(selector,bk,color,size,border,stprefix){
var i;
var v=getElementsBySelector(selector);
if(!stprefix){
  stprefix="r";
}
for(i=0;i<v.length;i++)
    AddBorderBottom(v[i],bk,color,size,border,stprefix);
}

function AddTop(el,bk,color,size,border,stprefix){
var i;
var d=document.createElement("b");
var cn=stprefix;
var lim=4;
if(size && size=="small"){ cn=stprefix+"s"; lim=2}
d.className="rtop";
d.style.backgroundColor=bk;
for(i=1;i<=lim;i++){
    var x=document.createElement("b");
    x.className=cn + i+borderAtt;
    x.style.backgroundColor=color;
    d.appendChild(x);
    }

   el.insertBefore(d,el.firstChild);
}

function AddBorderTop(el, bk, color, size, border,stprefix) {
    var i;

    var d = document.createElement("b");

    var cn = stprefix;
    var lim = 4;
    if (size && size == "small") {
        cn = stprefix+"s";
        lim = 2
    }

    d.className = stprefix+"topborder";
    d.style.backgroundColor = bk;
    for (i = 1; i <= lim; i++) {
        var x = document.createElement("b");

        x.className = cn + i + "border";
        if (1 >= i) {
            x.style.backgroundColor = border;
        } else {
            x.style.backgroundColor = color;
        }
        d.appendChild(x);
    }

    el.insertBefore(d, el.firstChild);
}

function AddBorderBottom(el,bk,color,size,border,stprefix){
var i;
var d=document.createElement("b");
var cn=stprefix;
var lim=4;
if(size && size=="small"){ cn=stprefix+"s"; lim=2}
d.className=stprefix+"bottomborder";
d.style.backgroundColor=bk;
for(i=lim;i>0;i--){
    var x=document.createElement("b");
    x.className=cn + i+"border";
    if (1 >= i) {
        x.style.backgroundColor = border;
    } else {
        x.style.backgroundColor = color;
    }
    d.appendChild(x);
    }
el.appendChild(d,el.firstChild);
}

function AddBottom(el,bk,color,size,stprefix){
var i;
var d=document.createElement("b");
var cn=stprefix;
var lim=4;
if(size && size=="small"){ cn=stprefix+"s"; lim=2}
d.className=stprefix+"bottom";
d.style.backgroundColor=bk;
for(i=lim;i>0;i--){
    var x=document.createElement("b");
    x.className=cn + i;
    x.style.backgroundColor=color;
    d.appendChild(x);
    }
el.appendChild(d,el.firstChild);
}

function getElementsBySelector(selector){
var i;
var s=[];
var selid="";
var selclass="";
var tag=selector;
var objlist=[];
if(selector.indexOf(" ")>0){  //descendant selector like "tag#id tag"
    s=selector.split(" ");
    var fs=s[0].split("#");
    if(fs.length==1) return(objlist);
    return(document.getElementById(fs[1]).getElementsByTagName(s[1]));
    }
if(selector.indexOf("#")>0){ //id selector like "tag#id"
    s=selector.split("#");
    tag=s[0];
    selid=s[1];
    }
if(selid!=""){
    objlist.push(document.getElementById(selid));
    return(objlist);
    }
if(selector.indexOf(".")>0){  //class selector like "tag.class"
    s=selector.split(".");
    tag=s[0];
    selclass=s[1];
    }
var v=document.getElementsByTagName(tag);  // tag selector like "tag"
if(selclass=="")
    return(v);
for(i=0;i<v.length;i++){
    if(v[i].className==selclass){
        objlist.push(v[i]);
        }
    }
return(objlist);
}
