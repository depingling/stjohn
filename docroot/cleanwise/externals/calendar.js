
var g_MINY = 1601;
var g_MAXY = 4500;
var g_month = 0;
var g_day   = 0;
var g_year  = 0;
var g_yLow  = 1990;

var multiDate = 1;

function GetInputDate(t,f) {
  var l = t.length;
  if (0 == l) {
  var dt = new Date();
  g_month = dt.getMonth() + 1;
  g_day = dt.getDate();
    g_year = dt.getYear();
    return true;
  }
  var cSp = '\0';
  var sSp1 = "";
  var sSp2 = "";
  for (var i = 0; i < t.length; i++) {
    var c = t.charAt(i);
    if (c == ' ' || c == ',' || isdigit(c)) continue;
    else if(cSp == '\0' && (c == '/' || c == '-' || c == '.')) {
      cSp = c;
      sSp1 = t.substring(i+1,l);
    }
    else if(c == cSp) sSp2 = t.substring(i+1,l);
    else if(c != cSp) return false;
  }
  if (0 == sSp1.length) return false;
  var m;
  var d;
  var y;
  m = atoi(t);
  d = atoi(sSp1);
  if (0 != sSp2.length) y = atoi(sSp2);
  else y = DefYr(m,d);
  if (y < 100) {
    y = 1900 + y;
    while (y < g_yLow) y = y+100;
  }
  if (y < g_MINY || y > g_MAXY || m < 1 || m > 12) return false;
  if (d < 1 || d > GetMonthCount(m,y)) return false;
  g_month = m;
  g_day = d;
  g_year = y;
  return true;
}

function DefYr(m,d) {
  var dt = new Date();
  var yCur = (dt.getYear() < 1000) ? 1900+dt.getYear() : dt.getYear();
  if (m-1 < dt.getMonth() || (m-1 == dt.getMonth() && d < dt.getDate())) return 1+yCur;
  else return yCur;
}

function atoi(s) {
  var t = 0;
  for (var i = 0; i < s.length; i++) {
    var c = s.charAt(i);
    if(!isdigit(c)) return t;
    else t = t*10 + (c-'0');}
    return t;
}

function isdigit(c) {
  return(c >= '0' && c <= '9');
}

function GetMonthCount(m,y) {
  var c = rgMC[m-1];
  if ((2 == m) && IsLeapYear(y)) c++;
  return c;
}

function IsLeapYear(y) {
  if (0 == y % 4 && ((y % 100 != 0) || (y % 400 == 0))) return true;
  else return false;
}

var rgMC = new Array(12);
rgMC[0]  = 31;
rgMC[1]  = 28;
rgMC[2]  = 31;
rgMC[3]  = 30;
rgMC[4]  = 31;
rgMC[5]  = 30;
rgMC[6]  = 31;
rgMC[7]  = 31;
rgMC[8]  = 30;
rgMC[9]  = 31;
rgMC[10] = 30;
rgMC[11] = 31;
var g_eC = null;
var g_eCV = "";
var g_dFmt = "mmddyy";
var g_fnCB = null;

function ShowCalendar(eP,eD,eDP,dmin,dmax,fnCB,mDate) {
  var dF = document.all.CalFrame;
  var wF = window.frames.CalFrame;
  if (mDate != null) multiDate = mDate;

  if (null == wF.g_fCalLoaded || false == wF.g_fCalLoaded) {
    alert("Unable to load popup calendar.\r\nPlease reload the page.");
    return;
  }

  dtMin = new Date();
  dtMin.setDate(dtMin.getDate()+dmin);
  dtMax = new Date();
  dtMax.setDate(dtMax.getDate()+dmax);
  wF.SetMinMax(new Date(dtMin),new Date(dtMax));
  g_fnCB = fnCB;
  if (eD == g_eC && "block"==dF.style.display) {
    if (g_eCV != eD.value && GetInputDate(eD.value,g_dFmt)) {
      wF.SetInputDate(g_day,g_month,g_year);
      wF.SetDate(g_day,g_month,g_year);
      g_eCV = eD.value;
    }
    else dF.style.display="none";
  }
  else {
    if (GetInputDate(eD.value,g_dFmt)) {
      wF.SetInputDate(g_day,g_month,g_year);
      wF.SetDate(g_day,g_month,g_year);
    }
    else if(null != eDP && GetInputDate(eDP.value,g_dFmt)) {
      wF.SetInputDate(g_day,g_month,g_year);
      wF.SetDate(g_day,g_month,g_year);
    }
    else {
      var dt = new Date(dtMin);
      wF.SetInputDate(-1,-1,-1);
      wF.SetDate(dt.getDate(),dt.getMonth()+1,dt.getFullYear());
    }
    // 148 is width of popup. 170 allows for vertical scroll bar.
    if (eP.offsetLeft + 170 < eP.document.body.clientWidth)
      dF.style.left = eP.offsetLeft;
    else
      dF.style.left = eP.offsetLeft - (eP.offsetLeft + 170 - eP.document.body.clientWidth);
    var eT = eP.offsetTop;
    var eH = eP.offsetHeight;
    var dH = dF.style.pixelHeight;
    var sT = document.body.scrollTop;
    if (eT-dH >= sT && eT+eH+dH > document.body.clientHeight+sT) dF.style.top = eT-dH;
    else dF.style.top = eT+eH;
    if ("none" == dF.style.display) dF.style.display="block";
    g_eC = eD;
    g_eCV = eD.value;
//alert("eD.value is "+eD.value);
  }
  return false;
}

function SetDate(d,m,y) {
  var ds = "/";
  g_eC.focus();

// multiple values for also/delete date(s)
if (multiDate == 1) {
  if(g_eC.value != ""){
  g_eC.value += ", ";
  }
} else {
  g_eC.value = "";
}



  g_eC.value += m+ds+d+ds+y;

  g_eCV = g_eC.value;
  if (null != g_fnCB && "" != g_fnCB)
    eval(g_fnCB);
}

function GetDowStart() {
  return 0;
}

function GetDOW2(d,m,y) {
  var dt = new Date(y,m-1,d);
  return(dt.getDay()+(7-GetDowStart()))%7;
}

function LoadMonths(n) {
  var dt = new Date();
  var m = dt.getMonth()+1;
  var y = dt.getFullYear();
  var rg = new Array(n);
  for (i = 0; i < n; i++) {
    rg[i] = document.createElement("IMG");
    rg[i].src = "../externals/images/cal/w" + GetDOW2(1,m,y) + "d" + GetMonthCount(m,y) + ".gif";
    m++;
    if (12 < m) {
      m = 1;
      y++;
    }
  }
}

LoadMonths(12);
