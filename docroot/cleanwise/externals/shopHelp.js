
function shopHelp (eP, type) {
  if(navigator.appName.indexOf("Netscape") !=-1 ) return; //Only IE so far
  var dF;
  
  if(type == "OrderGuide"){    
    dF = document.all.orderGuideHelp;
  }else if (type == "LastOrder"){
    dF = document.all.lastOrderHelp;
  }else{ //janitors closet
    dF = document.all.janitorClosetHelp;
  }
  dF.style.left = eP.offsetLeft+70;
  var eT = eP.style.pixelTop;
  var eH = eP.offsetHeight;
  var dH = dF.style.pixelHeight;
  var sT = document.body.scrollTop;
  if (eT-dH >= sT && eT+eH+dH > document.body.clientHeight+sT) dF.style.top = eT-dH;
  else dF.style.top = eT+eH;
  if ("none" == dF.style.display) dF.style.display="block";
}
