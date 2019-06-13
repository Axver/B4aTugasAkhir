package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class clsslidingsidebar extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "b4a.example.clsslidingsidebar");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", b4a.example.clsslidingsidebar.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlcontent = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlsidebar = null;
public anywheresoftware.b4a.objects.PanelWrapper _pnlgesture = null;
public anywheresoftware.b4a.objects.PanelWrapper _sbparent = null;
public byte _sbposition = (byte)0;
public byte _sbanimtype = (byte)0;
public float _sbinterpolator = 0f;
public byte _sbaniminprogress = (byte)0;
public boolean _sbstopanim = false;
public int _sbopenduration = 0;
public int _sbcloseduration = 0;
public boolean _sbisvisible = false;
public boolean _sbisopening = false;
public int _sbstartx = 0;
public int _sbstarty = 0;
public int _pnlsidebarstartx = 0;
public int _pnlsidebarstarty = 0;
public int _pnlcontentstartx = 0;
public int _pnlcontentstarty = 0;
public anywheresoftware.b4a.objects.ConcreteViewWrapper _sbhandle = null;
public byte _sbfinalmovement = (byte)0;
public String _sbsubfullyopen = "";
public String _sbsubfullyclosed = "";
public String _sbsubmove = "";
public Object _sbmodule = null;
public boolean _from_open = false;
public boolean _from_close = false;
public boolean _open_anim = false;
public boolean _close_anim = false;
public byte _opening = (byte)0;
public byte _closing = (byte)0;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.menu _menu = null;
public b4a.example.layer _layer = null;
public b4a.example.citizen _citizen = null;
public b4a.example.building _building = null;
public String  _addopenclosehandle(anywheresoftware.b4a.objects.ConcreteViewWrapper _hdl,int _position,int _width,int _height,byte _finalmovement) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 336;BA.debugLine="Public Sub AddOpenCloseHandle(Hdl As View, Positio";
 //BA.debugLineNum = 337;BA.debugLine="If Hdl = Null Then Return";
if (_hdl== null) { 
if (true) return "";};
 //BA.debugLineNum = 339;BA.debugLine="sbHandle = Hdl";
_sbhandle = _hdl;
 //BA.debugLineNum = 340;BA.debugLine="Select sbPosition";
switch (BA.switchObjectToInt(_sbposition,(byte) (0),(byte) (1),(byte) (2),(byte) (3))) {
case 0: {
 //BA.debugLineNum = 342;BA.debugLine="sbParent.AddView(Hdl, pnlContent.Left, Position";
_sbparent.AddView((android.view.View)(_hdl.getObject()),_pnlcontent.getLeft(),_position,_width,_height);
 break; }
case 1: {
 //BA.debugLineNum = 344;BA.debugLine="sbParent.AddView(Hdl, pnlContent.Left + pnlCont";
_sbparent.AddView((android.view.View)(_hdl.getObject()),(int) (_pnlcontent.getLeft()+_pnlcontent.getWidth()-_width),_position,_width,_height);
 break; }
case 2: {
 //BA.debugLineNum = 346;BA.debugLine="sbParent.AddView(Hdl, Position, pnlContent.Top,";
_sbparent.AddView((android.view.View)(_hdl.getObject()),_position,_pnlcontent.getTop(),_width,_height);
 break; }
case 3: {
 //BA.debugLineNum = 348;BA.debugLine="sbParent.AddView(Hdl, Position, pnlContent.Top";
_sbparent.AddView((android.view.View)(_hdl.getObject()),_position,(int) (_pnlcontent.getTop()+_pnlcontent.getHeight()-_height),_width,_height);
 break; }
}
;
 //BA.debugLineNum = 350;BA.debugLine="sbFinalMovement = FinalMovement";
_sbfinalmovement = _finalmovement;
 //BA.debugLineNum = 352;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 353;BA.debugLine="r.Target = Hdl";
_r.Target = (Object)(_hdl.getObject());
 //BA.debugLineNum = 354;BA.debugLine="r.SetOnTouchListener(\"Gesture_onTouch\")";
_r.SetOnTouchListener(ba,"Gesture_onTouch");
 //BA.debugLineNum = 355;BA.debugLine="End Sub";
return "";
}
public String  _animate(int _progression) throws Exception{
 //BA.debugLineNum = 178;BA.debugLine="Private Sub Animate(Progression As Int)";
 //BA.debugLineNum = 179;BA.debugLine="Select sbPosition";
switch (BA.switchObjectToInt(_sbposition,(byte) (0),(byte) (1),(byte) (2),(byte) (3))) {
case 0: {
 //BA.debugLineNum = 181;BA.debugLine="If sbAnimType > 0 Then pnlSidebar.Left = pnlSid";
if (_sbanimtype>0) { 
_pnlsidebar.setLeft((int) (_pnlsidebarstartx+_progression));};
 //BA.debugLineNum = 182;BA.debugLine="If sbAnimType < 2 Then pnlContent.Left = pnlCon";
if (_sbanimtype<2) { 
_pnlcontent.setLeft((int) (_pnlcontentstartx+_progression));};
 //BA.debugLineNum = 183;BA.debugLine="If sbAnimType = 2 Then";
if (_sbanimtype==2) { 
 //BA.debugLineNum = 184;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Left =";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setLeft((int) (_pnlsidebar.getLeft()+_pnlsidebar.getWidth()));};
 //BA.debugLineNum = 185;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.Le";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setLeft((int) (_pnlsidebar.getLeft()+_pnlsidebar.getWidth()-(_pnlgesture.getWidth()/(double)2)));};
 }else {
 //BA.debugLineNum = 187;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Left =";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setLeft(_pnlcontent.getLeft());};
 //BA.debugLineNum = 188;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.Le";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setLeft((int) (_pnlcontent.getLeft()-(_pnlgesture.getWidth()/(double)2)));};
 };
 break; }
case 1: {
 //BA.debugLineNum = 191;BA.debugLine="If sbAnimType > 0 Then pnlSidebar.Left = pnlSid";
if (_sbanimtype>0) { 
_pnlsidebar.setLeft((int) (_pnlsidebarstartx+_progression));};
 //BA.debugLineNum = 192;BA.debugLine="If sbAnimType < 2 Then pnlContent.Left = pnlCon";
if (_sbanimtype<2) { 
_pnlcontent.setLeft((int) (_pnlcontentstartx+_progression));};
 //BA.debugLineNum = 193;BA.debugLine="If sbAnimType = 2 Then";
if (_sbanimtype==2) { 
 //BA.debugLineNum = 194;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Left =";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setLeft((int) (_pnlsidebar.getLeft()-_sbhandle.getWidth()));};
 //BA.debugLineNum = 195;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.Le";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setLeft((int) (_pnlsidebar.getLeft()-(_pnlgesture.getWidth()/(double)2)));};
 }else {
 //BA.debugLineNum = 197;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Left =";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setLeft((int) (_pnlcontent.getLeft()+_pnlcontent.getWidth()-_sbhandle.getWidth()));};
 //BA.debugLineNum = 198;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.Le";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setLeft((int) (_pnlcontent.getLeft()+_pnlcontent.getWidth()-(_pnlgesture.getWidth()/(double)2)));};
 };
 break; }
case 2: {
 //BA.debugLineNum = 201;BA.debugLine="If sbAnimType > 0 Then pnlSidebar.Top = pnlSide";
if (_sbanimtype>0) { 
_pnlsidebar.setTop((int) (_pnlsidebarstarty+_progression));};
 //BA.debugLineNum = 202;BA.debugLine="If sbAnimType < 2 Then pnlContent.Top = pnlCont";
if (_sbanimtype<2) { 
_pnlcontent.setTop((int) (_pnlcontentstarty+_progression));};
 //BA.debugLineNum = 203;BA.debugLine="If sbAnimType = 2 Then";
if (_sbanimtype==2) { 
 //BA.debugLineNum = 204;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Top =";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setTop((int) (_pnlsidebar.getTop()+_pnlsidebar.getHeight()));};
 //BA.debugLineNum = 205;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.To";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setTop((int) (_pnlsidebar.getTop()+_pnlsidebar.getHeight()-(_pnlgesture.getHeight()/(double)2)));};
 }else {
 //BA.debugLineNum = 207;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Top =";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setTop(_pnlcontent.getTop());};
 //BA.debugLineNum = 208;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.To";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setTop((int) (_pnlcontent.getTop()-(_pnlgesture.getHeight()/(double)2)));};
 };
 break; }
case 3: {
 //BA.debugLineNum = 211;BA.debugLine="If sbAnimType > 0 Then pnlSidebar.Top = pnlSide";
if (_sbanimtype>0) { 
_pnlsidebar.setTop((int) (_pnlsidebarstarty+_progression));};
 //BA.debugLineNum = 212;BA.debugLine="If sbAnimType < 2 Then pnlContent.Top = pnlCont";
if (_sbanimtype<2) { 
_pnlcontent.setTop((int) (_pnlcontentstarty+_progression));};
 //BA.debugLineNum = 213;BA.debugLine="If sbAnimType = 2 Then";
if (_sbanimtype==2) { 
 //BA.debugLineNum = 214;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Top =";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setTop((int) (_pnlsidebar.getTop()-_sbhandle.getHeight()));};
 //BA.debugLineNum = 215;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.To";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setTop((int) (_pnlsidebar.getTop()-(_pnlgesture.getHeight()/(double)2)));};
 }else {
 //BA.debugLineNum = 217;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Top =";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setTop((int) (_pnlcontent.getTop()+_pnlcontent.getHeight()-_sbhandle.getHeight()));};
 //BA.debugLineNum = 218;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.To";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setTop((int) (_pnlcontent.getTop()+_pnlcontent.getHeight()-(_pnlgesture.getHeight()/(double)2)));};
 };
 break; }
}
;
 //BA.debugLineNum = 221;BA.debugLine="sbIsVisible = (CalcDistance(FROM_CLOSE) <> 0)";
_sbisvisible = (_calcdistance(_from_close)!=0);
 //BA.debugLineNum = 222;BA.debugLine="End Sub";
return "";
}
public String  _animatesidebar(boolean _close) throws Exception{
int _animdistance = 0;
int _animduration = 0;
float _pctstilltomove = 0f;
float _progression = 0f;
long _endtime = 0L;
long _deltatime = 0L;
 //BA.debugLineNum = 224;BA.debugLine="Private Sub AnimateSidebar(Close As Boolean)";
 //BA.debugLineNum = 226;BA.debugLine="Dim AnimDistance, AnimDuration As Int";
_animdistance = 0;
_animduration = 0;
 //BA.debugLineNum = 227;BA.debugLine="Dim PctStillToMove As Float";
_pctstilltomove = 0f;
 //BA.debugLineNum = 228;BA.debugLine="If Close Then";
if (_close) { 
 //BA.debugLineNum = 229;BA.debugLine="AnimDistance = CalcDistance(FROM_CLOSE)";
_animdistance = _calcdistance(_from_close);
 //BA.debugLineNum = 230;BA.debugLine="PctStillToMove = Abs(AnimDistance) / pnlSidebar.";
_pctstilltomove = (float) (__c.Abs(_animdistance)/(double)_pnlsidebar.getWidth());
 //BA.debugLineNum = 231;BA.debugLine="AnimDuration = sbCloseDuration * PctStillToMove";
_animduration = (int) (_sbcloseduration*_pctstilltomove);
 //BA.debugLineNum = 232;BA.debugLine="sbAnimInProgress = CLOSING";
_sbaniminprogress = _closing;
 }else {
 //BA.debugLineNum = 234;BA.debugLine="AnimDistance = CalcDistance(FROM_OPEN)";
_animdistance = _calcdistance(_from_open);
 //BA.debugLineNum = 235;BA.debugLine="PctStillToMove = Abs(AnimDistance) / pnlSidebar.";
_pctstilltomove = (float) (__c.Abs(_animdistance)/(double)_pnlsidebar.getWidth());
 //BA.debugLineNum = 236;BA.debugLine="AnimDuration = sbOpenDuration * PctStillToMove";
_animduration = (int) (_sbopenduration*_pctstilltomove);
 //BA.debugLineNum = 237;BA.debugLine="sbAnimInProgress = OPENING";
_sbaniminprogress = _opening;
 };
 //BA.debugLineNum = 239;BA.debugLine="If AnimDistance = 0 Then";
if (_animdistance==0) { 
 //BA.debugLineNum = 241;BA.debugLine="sbAnimInProgress = 0";
_sbaniminprogress = (byte) (0);
 //BA.debugLineNum = 242;BA.debugLine="TriggerFinalEvent";
_triggerfinalevent();
 //BA.debugLineNum = 243;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 246;BA.debugLine="pnlSidebarStartX = pnlSidebar.Left";
_pnlsidebarstartx = _pnlsidebar.getLeft();
 //BA.debugLineNum = 247;BA.debugLine="pnlSidebarStartY = pnlSidebar.Top";
_pnlsidebarstarty = _pnlsidebar.getTop();
 //BA.debugLineNum = 248;BA.debugLine="pnlContentStartX = pnlContent.Left";
_pnlcontentstartx = _pnlcontent.getLeft();
 //BA.debugLineNum = 249;BA.debugLine="pnlContentStartY = pnlContent.Top";
_pnlcontentstarty = _pnlcontent.getTop();
 //BA.debugLineNum = 252;BA.debugLine="sbStopAnim = False";
_sbstopanim = __c.False;
 //BA.debugLineNum = 253;BA.debugLine="Dim Progression As Float";
_progression = 0f;
 //BA.debugLineNum = 254;BA.debugLine="Dim EndTime, DeltaTime As Long";
_endtime = 0L;
_deltatime = 0L;
 //BA.debugLineNum = 255;BA.debugLine="EndTime = DateTime.Now + AnimDuration";
_endtime = (long) (__c.DateTime.getNow()+_animduration);
 //BA.debugLineNum = 256;BA.debugLine="Do While DateTime.Now < EndTime";
while (__c.DateTime.getNow()<_endtime) {
 //BA.debugLineNum = 257;BA.debugLine="DeltaTime = EndTime - DateTime.Now";
_deltatime = (long) (_endtime-__c.DateTime.getNow());
 //BA.debugLineNum = 258;BA.debugLine="Animate(Power(1 - (DeltaTime / AnimDuration), sb";
_animate((int) (__c.Power(1-(_deltatime/(double)_animduration),_sbinterpolator)*_animdistance));
 //BA.debugLineNum = 259;BA.debugLine="If SubExists(sbModule, sbSubMove) Then";
if (__c.SubExists(ba,_sbmodule,_sbsubmove)) { 
 //BA.debugLineNum = 261;BA.debugLine="CallSub2(sbModule, sbSubMove, Not(Close))";
__c.CallSubNew2(ba,_sbmodule,_sbsubmove,(Object)(__c.Not(_close)));
 };
 //BA.debugLineNum = 263;BA.debugLine="DoEvents 'Processes the draw messages and keeps";
__c.DoEvents();
 //BA.debugLineNum = 264;BA.debugLine="If sbStopAnim Then";
if (_sbstopanim) { 
 //BA.debugLineNum = 265;BA.debugLine="DeltaTime = 0";
_deltatime = (long) (0);
 //BA.debugLineNum = 266;BA.debugLine="Exit";
if (true) break;
 };
 }
;
 //BA.debugLineNum = 269;BA.debugLine="If DeltaTime <> 0 Then Animate(AnimDistance)";
if (_deltatime!=0) { 
_animate(_animdistance);};
 //BA.debugLineNum = 271;BA.debugLine="sbAnimInProgress = 0";
_sbaniminprogress = (byte) (0);
 //BA.debugLineNum = 272;BA.debugLine="TriggerFinalEvent";
_triggerfinalevent();
 //BA.debugLineNum = 273;BA.debugLine="End Sub";
return "";
}
public String  _block_touch(int _action,float _x,float _y) throws Exception{
 //BA.debugLineNum = 91;BA.debugLine="Private Sub Block_Touch(Action As Int, X As Float,";
 //BA.debugLineNum = 93;BA.debugLine="End Sub";
return "";
}
public String  _btn_click(Object _viewtag) throws Exception{
 //BA.debugLineNum = 316;BA.debugLine="Private Sub Btn_Click(ViewTag As Object)";
 //BA.debugLineNum = 317;BA.debugLine="If IsSidebarVisible Then";
if (_issidebarvisible()) { 
 //BA.debugLineNum = 318;BA.debugLine="If sbAnimInProgress = CLOSING Then";
if (_sbaniminprogress==_closing) { 
 //BA.debugLineNum = 319;BA.debugLine="OpenSidebar";
_opensidebar();
 }else {
 //BA.debugLineNum = 321;BA.debugLine="CloseSidebar";
_closesidebar();
 };
 }else {
 //BA.debugLineNum = 324;BA.debugLine="OpenSidebar";
_opensidebar();
 };
 //BA.debugLineNum = 326;BA.debugLine="End Sub";
return "";
}
public int  _calcdistance(boolean _fromopen) throws Exception{
 //BA.debugLineNum = 404;BA.debugLine="Private Sub CalcDistance(FromOpen As Boolean) As I";
 //BA.debugLineNum = 406;BA.debugLine="Select sbPosition";
switch (BA.switchObjectToInt(_sbposition,(byte) (0),(byte) (1),(byte) (2),(byte) (3))) {
case 0: {
 //BA.debugLineNum = 408;BA.debugLine="If sbAnimType = 2 Then";
if (_sbanimtype==2) { 
 //BA.debugLineNum = 409;BA.debugLine="If FromOpen Then";
if (_fromopen) { 
 //BA.debugLineNum = 410;BA.debugLine="Return - pnlSidebar.Left";
if (true) return (int) (-_pnlsidebar.getLeft());
 }else {
 //BA.debugLineNum = 412;BA.debugLine="Return - pnlSidebar.Left - pnlSidebar.Width";
if (true) return (int) (-_pnlsidebar.getLeft()-_pnlsidebar.getWidth());
 };
 }else {
 //BA.debugLineNum = 415;BA.debugLine="If FromOpen Then";
if (_fromopen) { 
 //BA.debugLineNum = 416;BA.debugLine="Return pnlSidebar.Width - pnlContent.Left";
if (true) return (int) (_pnlsidebar.getWidth()-_pnlcontent.getLeft());
 }else {
 //BA.debugLineNum = 418;BA.debugLine="Return - pnlContent.Left";
if (true) return (int) (-_pnlcontent.getLeft());
 };
 };
 break; }
case 1: {
 //BA.debugLineNum = 422;BA.debugLine="If sbAnimType = 2 Then";
if (_sbanimtype==2) { 
 //BA.debugLineNum = 423;BA.debugLine="If FromOpen Then";
if (_fromopen) { 
 //BA.debugLineNum = 424;BA.debugLine="Return - pnlSidebar.Left + pnlContent.Width -";
if (true) return (int) (-_pnlsidebar.getLeft()+_pnlcontent.getWidth()-_pnlsidebar.getWidth());
 }else {
 //BA.debugLineNum = 426;BA.debugLine="Return - pnlSidebar.Left + pnlContent.Width";
if (true) return (int) (-_pnlsidebar.getLeft()+_pnlcontent.getWidth());
 };
 }else {
 //BA.debugLineNum = 429;BA.debugLine="If FromOpen Then";
if (_fromopen) { 
 //BA.debugLineNum = 430;BA.debugLine="Return - pnlSidebar.Width - pnlContent.Left";
if (true) return (int) (-_pnlsidebar.getWidth()-_pnlcontent.getLeft());
 }else {
 //BA.debugLineNum = 432;BA.debugLine="Return - pnlContent.Left";
if (true) return (int) (-_pnlcontent.getLeft());
 };
 };
 break; }
case 2: {
 //BA.debugLineNum = 436;BA.debugLine="If sbAnimType = 2 Then";
if (_sbanimtype==2) { 
 //BA.debugLineNum = 437;BA.debugLine="If FromOpen Then";
if (_fromopen) { 
 //BA.debugLineNum = 438;BA.debugLine="Return - pnlSidebar.Top";
if (true) return (int) (-_pnlsidebar.getTop());
 }else {
 //BA.debugLineNum = 440;BA.debugLine="Return - pnlSidebar.Top - pnlSidebar.Height";
if (true) return (int) (-_pnlsidebar.getTop()-_pnlsidebar.getHeight());
 };
 }else {
 //BA.debugLineNum = 443;BA.debugLine="If FromOpen Then";
if (_fromopen) { 
 //BA.debugLineNum = 444;BA.debugLine="Return pnlSidebar.Height - pnlContent.Top";
if (true) return (int) (_pnlsidebar.getHeight()-_pnlcontent.getTop());
 }else {
 //BA.debugLineNum = 446;BA.debugLine="Return - pnlContent.Top";
if (true) return (int) (-_pnlcontent.getTop());
 };
 };
 break; }
case 3: {
 //BA.debugLineNum = 450;BA.debugLine="If sbAnimType = 2 Then";
if (_sbanimtype==2) { 
 //BA.debugLineNum = 451;BA.debugLine="If FromOpen Then";
if (_fromopen) { 
 //BA.debugLineNum = 452;BA.debugLine="Return - pnlSidebar.Top + pnlContent.Height -";
if (true) return (int) (-_pnlsidebar.getTop()+_pnlcontent.getHeight()-_pnlsidebar.getHeight());
 }else {
 //BA.debugLineNum = 454;BA.debugLine="Return - pnlSidebar.Top + pnlContent.Height";
if (true) return (int) (-_pnlsidebar.getTop()+_pnlcontent.getHeight());
 };
 }else {
 //BA.debugLineNum = 457;BA.debugLine="If FromOpen Then";
if (_fromopen) { 
 //BA.debugLineNum = 458;BA.debugLine="Return - pnlSidebar.Height - pnlContent.Top";
if (true) return (int) (-_pnlsidebar.getHeight()-_pnlcontent.getTop());
 }else {
 //BA.debugLineNum = 460;BA.debugLine="Return - pnlContent.Top";
if (true) return (int) (-_pnlcontent.getTop());
 };
 };
 break; }
}
;
 //BA.debugLineNum = 464;BA.debugLine="End Sub";
return 0;
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 5;BA.debugLine="Sub Class_Globals";
 //BA.debugLineNum = 6;BA.debugLine="Private pnlContent As Panel";
_pnlcontent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 7;BA.debugLine="Private pnlSidebar As Panel";
_pnlsidebar = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 8;BA.debugLine="Private pnlGesture As Panel";
_pnlgesture = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 9;BA.debugLine="Private sbParent As Panel";
_sbparent = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 10;BA.debugLine="Private sbPosition As Byte";
_sbposition = (byte)0;
 //BA.debugLineNum = 11;BA.debugLine="Private sbAnimType As Byte";
_sbanimtype = (byte)0;
 //BA.debugLineNum = 12;BA.debugLine="Private sbInterpolator As Float";
_sbinterpolator = 0f;
 //BA.debugLineNum = 13;BA.debugLine="Private sbAnimInProgress As Byte";
_sbaniminprogress = (byte)0;
 //BA.debugLineNum = 14;BA.debugLine="Private sbStopAnim As Boolean";
_sbstopanim = false;
 //BA.debugLineNum = 15;BA.debugLine="Private sbOpenDuration As Int";
_sbopenduration = 0;
 //BA.debugLineNum = 16;BA.debugLine="Private sbCloseDuration As Int";
_sbcloseduration = 0;
 //BA.debugLineNum = 17;BA.debugLine="Private sbIsVisible As Boolean";
_sbisvisible = false;
 //BA.debugLineNum = 18;BA.debugLine="Private sbIsOpening As Boolean";
_sbisopening = false;
 //BA.debugLineNum = 19;BA.debugLine="Private sbStartX, sbStartY As Int";
_sbstartx = 0;
_sbstarty = 0;
 //BA.debugLineNum = 20;BA.debugLine="Private pnlSidebarStartX, pnlSidebarStartY As Int";
_pnlsidebarstartx = 0;
_pnlsidebarstarty = 0;
 //BA.debugLineNum = 21;BA.debugLine="Private pnlContentStartX, pnlContentStartY As Int";
_pnlcontentstartx = 0;
_pnlcontentstarty = 0;
 //BA.debugLineNum = 22;BA.debugLine="Private sbHandle As View";
_sbhandle = new anywheresoftware.b4a.objects.ConcreteViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private sbFinalMovement As Byte";
_sbfinalmovement = (byte)0;
 //BA.debugLineNum = 24;BA.debugLine="Private sbSubFullyOpen As String";
_sbsubfullyopen = "";
 //BA.debugLineNum = 25;BA.debugLine="Private sbSubFullyClosed As String";
_sbsubfullyclosed = "";
 //BA.debugLineNum = 26;BA.debugLine="Private sbSubMove As String";
_sbsubmove = "";
 //BA.debugLineNum = 27;BA.debugLine="Private sbModule As Object";
_sbmodule = new Object();
 //BA.debugLineNum = 28;BA.debugLine="Private FROM_OPEN As Boolean:  FROM_OPEN = True";
_from_open = false;
 //BA.debugLineNum = 28;BA.debugLine="Private FROM_OPEN As Boolean:  FROM_OPEN = True";
_from_open = __c.True;
 //BA.debugLineNum = 29;BA.debugLine="Private FROM_CLOSE As Boolean: FROM_CLOSE = False";
_from_close = false;
 //BA.debugLineNum = 29;BA.debugLine="Private FROM_CLOSE As Boolean: FROM_CLOSE = False";
_from_close = __c.False;
 //BA.debugLineNum = 30;BA.debugLine="Private OPEN_ANIM As Boolean:  OPEN_ANIM = False";
_open_anim = false;
 //BA.debugLineNum = 30;BA.debugLine="Private OPEN_ANIM As Boolean:  OPEN_ANIM = False";
_open_anim = __c.False;
 //BA.debugLineNum = 31;BA.debugLine="Private CLOSE_ANIM As Boolean: CLOSE_ANIM = True";
_close_anim = false;
 //BA.debugLineNum = 31;BA.debugLine="Private CLOSE_ANIM As Boolean: CLOSE_ANIM = True";
_close_anim = __c.True;
 //BA.debugLineNum = 32;BA.debugLine="Private OPENING As Byte: OPENING = 1";
_opening = (byte)0;
 //BA.debugLineNum = 32;BA.debugLine="Private OPENING As Byte: OPENING = 1";
_opening = (byte) (1);
 //BA.debugLineNum = 33;BA.debugLine="Private CLOSING As Byte: CLOSING = 2";
_closing = (byte)0;
 //BA.debugLineNum = 33;BA.debugLine="Private CLOSING As Byte: CLOSING = 2";
_closing = (byte) (2);
 //BA.debugLineNum = 34;BA.debugLine="End Sub";
return "";
}
public String  _closesidebar() throws Exception{
 //BA.debugLineNum = 289;BA.debugLine="Public Sub CloseSidebar";
 //BA.debugLineNum = 290;BA.debugLine="If sbAnimInProgress = OPENING Then";
if (_sbaniminprogress==_opening) { 
 //BA.debugLineNum = 292;BA.debugLine="sbStopAnim = True";
_sbstopanim = __c.True;
 //BA.debugLineNum = 293;BA.debugLine="CallSubDelayed2(Me, \"AnimateSidebar\", CLOSE_ANIM";
__c.CallSubDelayed2(ba,this,"AnimateSidebar",(Object)(_close_anim));
 }else if(_sbaniminprogress==0) { 
 //BA.debugLineNum = 296;BA.debugLine="AnimateSidebar(CLOSE_ANIM)";
_animatesidebar(_close_anim);
 };
 //BA.debugLineNum = 298;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.PanelWrapper  _contentpanel() throws Exception{
 //BA.debugLineNum = 128;BA.debugLine="Public Sub ContentPanel As Panel";
 //BA.debugLineNum = 129;BA.debugLine="Return pnlContent";
if (true) return _pnlcontent;
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return null;
}
public String  _enableswipegesture(boolean _enabled,int _gestureareasize,byte _finalmovement) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 365;BA.debugLine="Public Sub EnableSwipeGesture(Enabled As Boolean,";
 //BA.debugLineNum = 366;BA.debugLine="If Not(Enabled) Then";
if (__c.Not(_enabled)) { 
 //BA.debugLineNum = 367;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.Remo";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.RemoveView();};
 //BA.debugLineNum = 368;BA.debugLine="pnlGesture = Null";
_pnlgesture.setObject((android.view.ViewGroup)(__c.Null));
 //BA.debugLineNum = 369;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 372;BA.debugLine="If pnlGesture.IsInitialized Then";
if (_pnlgesture.IsInitialized()) { 
 //BA.debugLineNum = 373;BA.debugLine="Select sbPosition";
switch (BA.switchObjectToInt(_sbposition,(byte) (0),(byte) (1),(byte) (2),(byte) (3))) {
case 0: {
 //BA.debugLineNum = 375;BA.debugLine="pnlGesture.SetLayout(pnlContent.Left - (Gestur";
_pnlgesture.SetLayout((int) (_pnlcontent.getLeft()-(_gestureareasize/(double)2)),(int) (0),_gestureareasize,_getparentheight());
 break; }
case 1: {
 //BA.debugLineNum = 377;BA.debugLine="pnlGesture.SetLayout(pnlContent.Left + pnlCont";
_pnlgesture.SetLayout((int) (_pnlcontent.getLeft()+_pnlcontent.getWidth()-(_gestureareasize/(double)2)),(int) (0),_gestureareasize,_getparentheight());
 break; }
case 2: {
 //BA.debugLineNum = 379;BA.debugLine="pnlGesture.SetLayout(0, pnlContent.Top - (Gest";
_pnlgesture.SetLayout((int) (0),(int) (_pnlcontent.getTop()-(_gestureareasize/(double)2)),_getparentwidth(),_gestureareasize);
 break; }
case 3: {
 //BA.debugLineNum = 381;BA.debugLine="pnlGesture.SetLayout(0, pnlContent.Top + pnlCo";
_pnlgesture.SetLayout((int) (0),(int) (_pnlcontent.getTop()+_pnlcontent.getHeight()-(_gestureareasize/(double)2)),_getparentwidth(),_gestureareasize);
 break; }
}
;
 }else {
 //BA.debugLineNum = 384;BA.debugLine="pnlGesture.Initialize(\"\")";
_pnlgesture.Initialize(ba,"");
 //BA.debugLineNum = 385;BA.debugLine="Select sbPosition";
switch (BA.switchObjectToInt(_sbposition,(byte) (0),(byte) (1),(byte) (2),(byte) (3))) {
case 0: {
 //BA.debugLineNum = 387;BA.debugLine="sbParent.AddView(pnlGesture, pnlContent.Left -";
_sbparent.AddView((android.view.View)(_pnlgesture.getObject()),(int) (_pnlcontent.getLeft()-(_gestureareasize/(double)2)),(int) (0),_gestureareasize,_getparentheight());
 break; }
case 1: {
 //BA.debugLineNum = 389;BA.debugLine="sbParent.AddView(pnlGesture, pnlContent.Left +";
_sbparent.AddView((android.view.View)(_pnlgesture.getObject()),(int) (_pnlcontent.getLeft()+_pnlcontent.getWidth()-(_gestureareasize/(double)2)),(int) (0),_gestureareasize,_getparentheight());
 break; }
case 2: {
 //BA.debugLineNum = 391;BA.debugLine="sbParent.AddView(pnlGesture, 0, pnlContent.Top";
_sbparent.AddView((android.view.View)(_pnlgesture.getObject()),(int) (0),(int) (_pnlcontent.getTop()-(_gestureareasize/(double)2)),_getparentwidth(),_gestureareasize);
 break; }
case 3: {
 //BA.debugLineNum = 393;BA.debugLine="sbParent.AddView(pnlGesture, 0, pnlContent.Top";
_sbparent.AddView((android.view.View)(_pnlgesture.getObject()),(int) (0),(int) (_pnlcontent.getTop()+_pnlcontent.getHeight()-(_gestureareasize/(double)2)),_getparentwidth(),_gestureareasize);
 break; }
}
;
 //BA.debugLineNum = 396;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 397;BA.debugLine="r.Target = pnlGesture";
_r.Target = (Object)(_pnlgesture.getObject());
 //BA.debugLineNum = 398;BA.debugLine="r.SetOnTouchListener(\"Gesture_onTouch\")";
_r.SetOnTouchListener(ba,"Gesture_onTouch");
 };
 //BA.debugLineNum = 400;BA.debugLine="sbFinalMovement = FinalMovement";
_sbfinalmovement = _finalmovement;
 //BA.debugLineNum = 401;BA.debugLine="End Sub";
return "";
}
public boolean  _gesture_ontouch(Object _viewtag,int _action,float _x,float _y,Object _motionevent) throws Exception{
int _oldpos = 0;
 //BA.debugLineNum = 466;BA.debugLine="Private Sub Gesture_onTouch(ViewTag As Object, Act";
 //BA.debugLineNum = 467;BA.debugLine="If Action = 0 Then";
if (_action==0) { 
 //BA.debugLineNum = 468;BA.debugLine="sbStopAnim = True";
_sbstopanim = __c.True;
 //BA.debugLineNum = 469;BA.debugLine="sbStartX = X";
_sbstartx = (int) (_x);
 //BA.debugLineNum = 470;BA.debugLine="sbStartY = Y";
_sbstarty = (int) (_y);
 }else if(_action==2) { 
 //BA.debugLineNum = 474;BA.debugLine="Dim OldPos As Int";
_oldpos = 0;
 //BA.debugLineNum = 475;BA.debugLine="Select sbPosition";
switch (BA.switchObjectToInt(_sbposition,(byte) (0),(byte) (1),(byte) (2),(byte) (3))) {
case 0: {
 //BA.debugLineNum = 477;BA.debugLine="If sbAnimType = 2 Then";
if (_sbanimtype==2) { 
 //BA.debugLineNum = 478;BA.debugLine="OldPos = pnlSidebar.Left";
_oldpos = _pnlsidebar.getLeft();
 //BA.debugLineNum = 479;BA.debugLine="pnlSidebar.Left = Max(-pnlSidebar.Width, Min(";
_pnlsidebar.setLeft((int) (__c.Max(-_pnlsidebar.getWidth(),__c.Min(_pnlsidebar.getLeft()+_x-_sbstartx,0))));
 //BA.debugLineNum = 480;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Left";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setLeft((int) (_sbhandle.getLeft()-_oldpos+_pnlsidebar.getLeft()));};
 //BA.debugLineNum = 481;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.L";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setLeft((int) (_pnlgesture.getLeft()-_oldpos+_pnlsidebar.getLeft()));};
 }else {
 //BA.debugLineNum = 483;BA.debugLine="OldPos = pnlContent.Left";
_oldpos = _pnlcontent.getLeft();
 //BA.debugLineNum = 484;BA.debugLine="pnlContent.Left = Max(0, Min(pnlContent.Left";
_pnlcontent.setLeft((int) (__c.Max(0,__c.Min(_pnlcontent.getLeft()+_x-_sbstartx,_pnlsidebar.getWidth()))));
 //BA.debugLineNum = 485;BA.debugLine="If sbAnimType = 1 Then pnlSidebar.Left = pnlC";
if (_sbanimtype==1) { 
_pnlsidebar.setLeft((int) (_pnlcontent.getLeft()-_pnlsidebar.getWidth()));};
 //BA.debugLineNum = 486;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Left";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setLeft((int) (_sbhandle.getLeft()-_oldpos+_pnlcontent.getLeft()));};
 //BA.debugLineNum = 487;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.L";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setLeft((int) (_pnlgesture.getLeft()-_oldpos+_pnlcontent.getLeft()));};
 };
 //BA.debugLineNum = 489;BA.debugLine="sbIsOpening = X > sbStartX";
_sbisopening = _x>_sbstartx;
 break; }
case 1: {
 //BA.debugLineNum = 491;BA.debugLine="If sbAnimType = 2 Then";
if (_sbanimtype==2) { 
 //BA.debugLineNum = 492;BA.debugLine="OldPos = pnlSidebar.Left";
_oldpos = _pnlsidebar.getLeft();
 //BA.debugLineNum = 493;BA.debugLine="pnlSidebar.Left = Max(pnlContent.Width - pnlS";
_pnlsidebar.setLeft((int) (__c.Max(_pnlcontent.getWidth()-_pnlsidebar.getWidth(),__c.Min(_pnlsidebar.getLeft()+_x-_sbstartx,_pnlcontent.getWidth()))));
 //BA.debugLineNum = 494;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Left";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setLeft((int) (_sbhandle.getLeft()-_oldpos+_pnlsidebar.getLeft()));};
 //BA.debugLineNum = 495;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.L";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setLeft((int) (_pnlgesture.getLeft()-_oldpos+_pnlsidebar.getLeft()));};
 }else {
 //BA.debugLineNum = 497;BA.debugLine="OldPos = pnlContent.Left";
_oldpos = _pnlcontent.getLeft();
 //BA.debugLineNum = 498;BA.debugLine="pnlContent.Left = Max(-pnlSidebar.Width, Min(";
_pnlcontent.setLeft((int) (__c.Max(-_pnlsidebar.getWidth(),__c.Min(_pnlcontent.getLeft()+_x-_sbstartx,0))));
 //BA.debugLineNum = 499;BA.debugLine="If sbAnimType = 1 Then pnlSidebar.Left = pnlC";
if (_sbanimtype==1) { 
_pnlsidebar.setLeft((int) (_pnlcontent.getLeft()+_pnlcontent.getWidth()));};
 //BA.debugLineNum = 500;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Left";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setLeft((int) (_sbhandle.getLeft()-_oldpos+_pnlcontent.getLeft()));};
 //BA.debugLineNum = 501;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.L";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setLeft((int) (_pnlgesture.getLeft()-_oldpos+_pnlcontent.getLeft()));};
 };
 //BA.debugLineNum = 503;BA.debugLine="sbIsOpening = X < sbStartX";
_sbisopening = _x<_sbstartx;
 break; }
case 2: {
 //BA.debugLineNum = 505;BA.debugLine="If sbAnimType = 2 Then";
if (_sbanimtype==2) { 
 //BA.debugLineNum = 506;BA.debugLine="OldPos = pnlSidebar.Top";
_oldpos = _pnlsidebar.getTop();
 //BA.debugLineNum = 507;BA.debugLine="pnlSidebar.Top = Max(-pnlSidebar.Height, Min(";
_pnlsidebar.setTop((int) (__c.Max(-_pnlsidebar.getHeight(),__c.Min(_pnlsidebar.getTop()+_y-_sbstarty,0))));
 //BA.debugLineNum = 508;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Top =";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setTop((int) (_sbhandle.getTop()-_oldpos+_pnlsidebar.getTop()));};
 //BA.debugLineNum = 509;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.T";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setTop((int) (_pnlgesture.getTop()-_oldpos+_pnlsidebar.getTop()));};
 }else {
 //BA.debugLineNum = 511;BA.debugLine="OldPos = pnlContent.Top";
_oldpos = _pnlcontent.getTop();
 //BA.debugLineNum = 512;BA.debugLine="pnlContent.Top = Max(0, Min(pnlContent.Top +";
_pnlcontent.setTop((int) (__c.Max(0,__c.Min(_pnlcontent.getTop()+_y-_sbstarty,_pnlsidebar.getHeight()))));
 //BA.debugLineNum = 513;BA.debugLine="If sbAnimType = 1 Then pnlSidebar.Top = pnlCo";
if (_sbanimtype==1) { 
_pnlsidebar.setTop((int) (_pnlcontent.getTop()-_pnlsidebar.getHeight()));};
 //BA.debugLineNum = 514;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Top =";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setTop((int) (_sbhandle.getTop()-_oldpos+_pnlcontent.getTop()));};
 //BA.debugLineNum = 515;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.T";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setTop((int) (_pnlgesture.getTop()-_oldpos+_pnlcontent.getTop()));};
 };
 //BA.debugLineNum = 517;BA.debugLine="sbIsOpening = Y > sbStartY";
_sbisopening = _y>_sbstarty;
 break; }
case 3: {
 //BA.debugLineNum = 519;BA.debugLine="If sbAnimType = 2 Then";
if (_sbanimtype==2) { 
 //BA.debugLineNum = 520;BA.debugLine="OldPos = pnlSidebar.Top";
_oldpos = _pnlsidebar.getTop();
 //BA.debugLineNum = 521;BA.debugLine="pnlSidebar.Top = Max(pnlContent.Height - pnlS";
_pnlsidebar.setTop((int) (__c.Max(_pnlcontent.getHeight()-_pnlsidebar.getHeight(),__c.Min(_pnlsidebar.getTop()+_y-_sbstarty,_pnlcontent.getHeight()))));
 //BA.debugLineNum = 522;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Top =";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setTop((int) (_sbhandle.getTop()-_oldpos+_pnlsidebar.getTop()));};
 //BA.debugLineNum = 523;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.T";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setTop((int) (_pnlgesture.getTop()-_oldpos+_pnlsidebar.getTop()));};
 }else {
 //BA.debugLineNum = 525;BA.debugLine="OldPos = pnlContent.Top";
_oldpos = _pnlcontent.getTop();
 //BA.debugLineNum = 526;BA.debugLine="pnlContent.Top = Max(-pnlSidebar.Height, Min(";
_pnlcontent.setTop((int) (__c.Max(-_pnlsidebar.getHeight(),__c.Min(_pnlcontent.getTop()+_y-_sbstarty,0))));
 //BA.debugLineNum = 527;BA.debugLine="If sbAnimType = 1 Then pnlSidebar.Top = pnlCo";
if (_sbanimtype==1) { 
_pnlsidebar.setTop((int) (_pnlcontent.getTop()+_pnlcontent.getHeight()));};
 //BA.debugLineNum = 528;BA.debugLine="If sbHandle.IsInitialized Then sbHandle.Top =";
if (_sbhandle.IsInitialized()) { 
_sbhandle.setTop((int) (_sbhandle.getTop()-_oldpos+_pnlcontent.getTop()));};
 //BA.debugLineNum = 529;BA.debugLine="If pnlGesture.IsInitialized Then pnlGesture.T";
if (_pnlgesture.IsInitialized()) { 
_pnlgesture.setTop((int) (_pnlgesture.getTop()-_oldpos+_pnlcontent.getTop()));};
 };
 //BA.debugLineNum = 531;BA.debugLine="sbIsOpening = Y < sbStartY";
_sbisopening = _y<_sbstarty;
 break; }
}
;
 //BA.debugLineNum = 533;BA.debugLine="sbIsVisible = (CalcDistance(FROM_CLOSE) <> 0)";
_sbisvisible = (_calcdistance(_from_close)!=0);
 //BA.debugLineNum = 536;BA.debugLine="If SubExists(sbModule, sbSubMove) Then";
if (__c.SubExists(ba,_sbmodule,_sbsubmove)) { 
 //BA.debugLineNum = 537;BA.debugLine="If sbIsOpening Then";
if (_sbisopening) { 
 //BA.debugLineNum = 538;BA.debugLine="CallSub2(sbModule, sbSubMove, True)";
__c.CallSubNew2(ba,_sbmodule,_sbsubmove,(Object)(__c.True));
 }else {
 //BA.debugLineNum = 540;BA.debugLine="CallSub2(sbModule, sbSubMove, False)";
__c.CallSubNew2(ba,_sbmodule,_sbsubmove,(Object)(__c.False));
 };
 };
 }else if(_action==1) { 
 //BA.debugLineNum = 546;BA.debugLine="If sbFinalMovement > 0 And sbIsVisible Then";
if (_sbfinalmovement>0 && _sbisvisible) { 
 //BA.debugLineNum = 547;BA.debugLine="If sbFinalMovement = 2 Then sbIsOpening = Abs(C";
if (_sbfinalmovement==2) { 
_sbisopening = __c.Abs(_calcdistance(_from_close))>=__c.Abs(_calcdistance(_from_open));};
 //BA.debugLineNum = 548;BA.debugLine="If sbIsOpening Then";
if (_sbisopening) { 
 //BA.debugLineNum = 549;BA.debugLine="CallSubDelayed2(Me, \"AnimateSidebar\", OPEN_ANI";
__c.CallSubDelayed2(ba,this,"AnimateSidebar",(Object)(_open_anim));
 }else {
 //BA.debugLineNum = 551;BA.debugLine="CallSubDelayed2(Me, \"AnimateSidebar\", CLOSE_AN";
__c.CallSubDelayed2(ba,this,"AnimateSidebar",(Object)(_close_anim));
 };
 }else {
 //BA.debugLineNum = 554;BA.debugLine="TriggerFinalEvent";
_triggerfinalevent();
 };
 };
 //BA.debugLineNum = 557;BA.debugLine="Return True";
if (true) return __c.True;
 //BA.debugLineNum = 558;BA.debugLine="End Sub";
return false;
}
public int  _getparentheight() throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
int _realheight = 0;
 //BA.debugLineNum = 152;BA.debugLine="Private Sub getParentHeight As Int";
 //BA.debugLineNum = 153;BA.debugLine="If sbParent.Height < 0 Then";
if (_sbparent.getHeight()<0) { 
 //BA.debugLineNum = 154;BA.debugLine="Dim r As Reflector, RealHeight As Int";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
_realheight = 0;
 //BA.debugLineNum = 155;BA.debugLine="r.Target = sbParent";
_r.Target = (Object)(_sbparent.getObject());
 //BA.debugLineNum = 156;BA.debugLine="RealHeight = r.RunMethod(\"getHeight\")";
_realheight = (int)(BA.ObjectToNumber(_r.RunMethod("getHeight")));
 //BA.debugLineNum = 157;BA.debugLine="If RealHeight = 0 Then";
if (_realheight==0) { 
 //BA.debugLineNum = 158;BA.debugLine="DoEvents";
__c.DoEvents();
 //BA.debugLineNum = 159;BA.debugLine="RealHeight = r.RunMethod(\"getHeight\")";
_realheight = (int)(BA.ObjectToNumber(_r.RunMethod("getHeight")));
 };
 //BA.debugLineNum = 161;BA.debugLine="Return RealHeight";
if (true) return _realheight;
 }else {
 //BA.debugLineNum = 163;BA.debugLine="Return sbParent.Height";
if (true) return _sbparent.getHeight();
 };
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return 0;
}
public int  _getparentwidth() throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
int _realwidth = 0;
 //BA.debugLineNum = 135;BA.debugLine="Private Sub getParentWidth As Int";
 //BA.debugLineNum = 136;BA.debugLine="If sbParent.Width < 0 Then";
if (_sbparent.getWidth()<0) { 
 //BA.debugLineNum = 137;BA.debugLine="Dim r As Reflector, RealWidth As Int";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
_realwidth = 0;
 //BA.debugLineNum = 138;BA.debugLine="r.Target = sbParent";
_r.Target = (Object)(_sbparent.getObject());
 //BA.debugLineNum = 139;BA.debugLine="RealWidth = r.RunMethod(\"getWidth\")";
_realwidth = (int)(BA.ObjectToNumber(_r.RunMethod("getWidth")));
 //BA.debugLineNum = 140;BA.debugLine="If RealWidth = 0 Then";
if (_realwidth==0) { 
 //BA.debugLineNum = 141;BA.debugLine="DoEvents";
__c.DoEvents();
 //BA.debugLineNum = 142;BA.debugLine="RealWidth = r.RunMethod(\"getWidth\")";
_realwidth = (int)(BA.ObjectToNumber(_r.RunMethod("getWidth")));
 };
 //BA.debugLineNum = 144;BA.debugLine="Return RealWidth";
if (true) return _realwidth;
 }else {
 //BA.debugLineNum = 146;BA.debugLine="Return sbParent.Width";
if (true) return _sbparent.getWidth();
 };
 //BA.debugLineNum = 148;BA.debugLine="End Sub";
return 0;
}
public String  _initialize(anywheresoftware.b4a.BA _ba,anywheresoftware.b4a.objects.PanelWrapper _parent,int _sidebarsize,byte _sidebarposition,byte _animtype,int _openduration,int _closeduration) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 49;BA.debugLine="Public Sub Initialize(Parent As Panel, SidebarSize";
 //BA.debugLineNum = 50;BA.debugLine="sbParent = Parent";
_sbparent = _parent;
 //BA.debugLineNum = 51;BA.debugLine="pnlContent.Initialize(\"Block\")";
_pnlcontent.Initialize(ba,"Block");
 //BA.debugLineNum = 52;BA.debugLine="Parent.AddView(pnlContent, 0, 0, getParentWidth,";
_parent.AddView((android.view.View)(_pnlcontent.getObject()),(int) (0),(int) (0),_getparentwidth(),_getparentheight());
 //BA.debugLineNum = 54;BA.debugLine="pnlSidebar.Initialize(\"Block\")";
_pnlsidebar.Initialize(ba,"Block");
 //BA.debugLineNum = 55;BA.debugLine="Select SidebarPosition";
switch (BA.switchObjectToInt(_sidebarposition,(byte) (0),(byte) (1),(byte) (2),(byte) (3))) {
case 0: {
 //BA.debugLineNum = 57;BA.debugLine="If AnimType = 0 Then";
if (_animtype==0) { 
 //BA.debugLineNum = 58;BA.debugLine="Parent.AddView(pnlSidebar, 0, 0, SidebarSize,";
_parent.AddView((android.view.View)(_pnlsidebar.getObject()),(int) (0),(int) (0),_sidebarsize,_getparentheight());
 }else {
 //BA.debugLineNum = 60;BA.debugLine="Parent.AddView(pnlSidebar, -SidebarSize, 0, Si";
_parent.AddView((android.view.View)(_pnlsidebar.getObject()),(int) (-_sidebarsize),(int) (0),_sidebarsize,_getparentheight());
 };
 break; }
case 1: {
 //BA.debugLineNum = 63;BA.debugLine="If AnimType = 0 Then";
if (_animtype==0) { 
 //BA.debugLineNum = 64;BA.debugLine="Parent.AddView(pnlSidebar, getParentWidth - Si";
_parent.AddView((android.view.View)(_pnlsidebar.getObject()),(int) (_getparentwidth()-_sidebarsize),(int) (0),_sidebarsize,_getparentheight());
 }else {
 //BA.debugLineNum = 66;BA.debugLine="Parent.AddView(pnlSidebar, getParentWidth, 0,";
_parent.AddView((android.view.View)(_pnlsidebar.getObject()),_getparentwidth(),(int) (0),_sidebarsize,_getparentheight());
 };
 break; }
case 2: {
 //BA.debugLineNum = 69;BA.debugLine="If AnimType = 0 Then";
if (_animtype==0) { 
 //BA.debugLineNum = 70;BA.debugLine="Parent.AddView(pnlSidebar, 0, 0, getParentWidt";
_parent.AddView((android.view.View)(_pnlsidebar.getObject()),(int) (0),(int) (0),_getparentwidth(),_sidebarsize);
 }else {
 //BA.debugLineNum = 72;BA.debugLine="Parent.AddView(pnlSidebar, 0, -SidebarSize, ge";
_parent.AddView((android.view.View)(_pnlsidebar.getObject()),(int) (0),(int) (-_sidebarsize),_getparentwidth(),_sidebarsize);
 };
 break; }
case 3: {
 //BA.debugLineNum = 75;BA.debugLine="If AnimType = 0 Then";
if (_animtype==0) { 
 //BA.debugLineNum = 76;BA.debugLine="Parent.AddView(pnlSidebar, 0, getParentHeight";
_parent.AddView((android.view.View)(_pnlsidebar.getObject()),(int) (0),(int) (_getparentheight()-_sidebarsize),_getparentwidth(),_sidebarsize);
 }else {
 //BA.debugLineNum = 78;BA.debugLine="Parent.AddView(pnlSidebar, 0, getParentHeight,";
_parent.AddView((android.view.View)(_pnlsidebar.getObject()),(int) (0),_getparentheight(),_getparentwidth(),_sidebarsize);
 };
 break; }
}
;
 //BA.debugLineNum = 81;BA.debugLine="If AnimType = 0 Then pnlSidebar.SendToBack";
if (_animtype==0) { 
_pnlsidebar.SendToBack();};
 //BA.debugLineNum = 83;BA.debugLine="sbPosition = SidebarPosition";
_sbposition = _sidebarposition;
 //BA.debugLineNum = 84;BA.debugLine="sbAnimType = AnimType";
_sbanimtype = _animtype;
 //BA.debugLineNum = 85;BA.debugLine="sbInterpolator = 0.7";
_sbinterpolator = (float) (0.7);
 //BA.debugLineNum = 86;BA.debugLine="sbOpenDuration = OpenDuration";
_sbopenduration = _openduration;
 //BA.debugLineNum = 87;BA.debugLine="sbCloseDuration = CloseDuration";
_sbcloseduration = _closeduration;
 //BA.debugLineNum = 88;BA.debugLine="sbIsVisible = False";
_sbisvisible = __c.False;
 //BA.debugLineNum = 89;BA.debugLine="End Sub";
return "";
}
public boolean  _issidebarvisible() throws Exception{
 //BA.debugLineNum = 301;BA.debugLine="Public Sub IsSidebarVisible As Boolean";
 //BA.debugLineNum = 302;BA.debugLine="Return sbIsVisible";
if (true) return _sbisvisible;
 //BA.debugLineNum = 303;BA.debugLine="End Sub";
return false;
}
public Object  _loaddrawable(String _name) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
int _id_drawable = 0;
 //BA.debugLineNum = 96;BA.debugLine="Public Sub LoadDrawable(Name As String) As Object";
 //BA.debugLineNum = 97;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 98;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(ba));
 //BA.debugLineNum = 99;BA.debugLine="r.Target = r.RunMethod(\"getResources\")";
_r.Target = _r.RunMethod("getResources");
 //BA.debugLineNum = 100;BA.debugLine="r.Target = r.RunMethod(\"getSystem\")";
_r.Target = _r.RunMethod("getSystem");
 //BA.debugLineNum = 101;BA.debugLine="Dim ID_Drawable As Int";
_id_drawable = 0;
 //BA.debugLineNum = 102;BA.debugLine="ID_Drawable = r.RunMethod4(\"getIdentifier\", Array";
_id_drawable = (int)(BA.ObjectToNumber(_r.RunMethod4("getIdentifier",new Object[]{(Object)(_name),(Object)("drawable"),(Object)("android")},new String[]{"java.lang.String","java.lang.String","java.lang.String"})));
 //BA.debugLineNum = 104;BA.debugLine="r.Target = r.GetContext";
_r.Target = (Object)(_r.GetContext(ba));
 //BA.debugLineNum = 105;BA.debugLine="r.Target = r.RunMethod(\"getResources\")";
_r.Target = _r.RunMethod("getResources");
 //BA.debugLineNum = 106;BA.debugLine="Return r.RunMethod2(\"getDrawable\", ID_Drawable, \"";
if (true) return _r.RunMethod2("getDrawable",BA.NumberToString(_id_drawable),"java.lang.int");
 //BA.debugLineNum = 107;BA.debugLine="End Sub";
return null;
}
public String  _opensidebar() throws Exception{
 //BA.debugLineNum = 277;BA.debugLine="Public Sub OpenSidebar";
 //BA.debugLineNum = 278;BA.debugLine="If sbAnimInProgress = CLOSING Then";
if (_sbaniminprogress==_closing) { 
 //BA.debugLineNum = 280;BA.debugLine="sbStopAnim = True";
_sbstopanim = __c.True;
 //BA.debugLineNum = 281;BA.debugLine="CallSubDelayed2(Me, \"AnimateSidebar\", OPEN_ANIM)";
__c.CallSubDelayed2(ba,this,"AnimateSidebar",(Object)(_open_anim));
 }else if(_sbaniminprogress==0) { 
 //BA.debugLineNum = 284;BA.debugLine="AnimateSidebar(OPEN_ANIM)";
_animatesidebar(_open_anim);
 };
 //BA.debugLineNum = 286;BA.debugLine="End Sub";
return "";
}
public String  _setinterpolator(float _value) throws Exception{
 //BA.debugLineNum = 174;BA.debugLine="Public Sub SetInterpolator(Value As Float)";
 //BA.debugLineNum = 175;BA.debugLine="sbInterpolator = Value";
_sbinterpolator = _value;
 //BA.debugLineNum = 176;BA.debugLine="End Sub";
return "";
}
public String  _setonchangelisteners(Object _module,String _subonfullyopen,String _subonfullyclosed,String _subonmove) throws Exception{
 //BA.debugLineNum = 117;BA.debugLine="Public Sub SetOnChangeListeners(Module As Object,";
 //BA.debugLineNum = 118;BA.debugLine="sbModule = Module";
_sbmodule = _module;
 //BA.debugLineNum = 119;BA.debugLine="sbSubFullyOpen = SubOnFullyOpen";
_sbsubfullyopen = _subonfullyopen;
 //BA.debugLineNum = 120;BA.debugLine="sbSubFullyClosed = SubOnFullyClosed";
_sbsubfullyclosed = _subonfullyclosed;
 //BA.debugLineNum = 121;BA.debugLine="sbSubMove = SubOnMove";
_sbsubmove = _subonmove;
 //BA.debugLineNum = 122;BA.debugLine="End Sub";
return "";
}
public String  _setopenclosebutton(anywheresoftware.b4a.objects.ConcreteViewWrapper _btn) throws Exception{
anywheresoftware.b4a.agraham.reflection.Reflection _r = null;
 //BA.debugLineNum = 309;BA.debugLine="Public Sub SetOpenCloseButton(Btn As View)";
 //BA.debugLineNum = 310;BA.debugLine="If Btn = Null Then Return";
if (_btn== null) { 
if (true) return "";};
 //BA.debugLineNum = 311;BA.debugLine="Dim r As Reflector";
_r = new anywheresoftware.b4a.agraham.reflection.Reflection();
 //BA.debugLineNum = 312;BA.debugLine="r.Target = Btn";
_r.Target = (Object)(_btn.getObject());
 //BA.debugLineNum = 313;BA.debugLine="r.SetOnClickListener(\"Btn_Click\")";
_r.SetOnClickListener(ba,"Btn_Click");
 //BA.debugLineNum = 314;BA.debugLine="End Sub";
return "";
}
public anywheresoftware.b4a.objects.PanelWrapper  _sidebar() throws Exception{
 //BA.debugLineNum = 124;BA.debugLine="Public Sub Sidebar As Panel";
 //BA.debugLineNum = 125;BA.debugLine="Return pnlSidebar";
if (true) return _pnlsidebar;
 //BA.debugLineNum = 126;BA.debugLine="End Sub";
return null;
}
public String  _triggerfinalevent() throws Exception{
 //BA.debugLineNum = 560;BA.debugLine="Sub TriggerFinalEvent";
 //BA.debugLineNum = 562;BA.debugLine="If CalcDistance(FROM_OPEN) = 0 And SubExists(sbMo";
if (_calcdistance(_from_open)==0 && __c.SubExists(ba,_sbmodule,_sbsubfullyopen)) { 
 //BA.debugLineNum = 563;BA.debugLine="CallSub(sbModule, sbSubFullyOpen)";
__c.CallSubNew(ba,_sbmodule,_sbsubfullyopen);
 }else if(_calcdistance(_from_close)==0 && __c.SubExists(ba,_sbmodule,_sbsubfullyclosed)) { 
 //BA.debugLineNum = 565;BA.debugLine="CallSub(sbModule, sbSubFullyClosed)";
__c.CallSubNew(ba,_sbmodule,_sbsubfullyclosed);
 };
 //BA.debugLineNum = 567;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
if (BA.fastSubCompare(sub, "ANIMATESIDEBAR"))
	return _animatesidebar((Boolean) args[0]);
return BA.SubDelegator.SubNotFound;
}
}
