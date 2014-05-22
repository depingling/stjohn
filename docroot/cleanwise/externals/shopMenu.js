
function shopMenu (eP) {
  if(navigator.appName.indexOf("Netscape") !=-1 ) return; //Only IE so far

  var dF = document.all.shopMenu;
  dF.style.left = eP.offsetLeft+168;
  var eT = eP.style.pixelTop;
  var eH = eP.offsetHeight;
  var dH = dF.style.pixelHeight;
  var sT = document.body.scrollTop;
  if (eT-dH >= sT && eT+eH+dH > document.body.clientHeight+sT) dF.style.top = eT-dH;
  else dF.style.top = eT+eH;
  if ("none" == dF.style.display) dF.style.display="block";
}
