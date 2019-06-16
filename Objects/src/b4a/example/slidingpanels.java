package b4a.example;


import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.B4AClass;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.debug.*;

public class slidingpanels extends B4AClass.ImplB4AClass implements BA.SubDelegator{
    private static java.util.HashMap<String, java.lang.reflect.Method> htSubs;
    private void innerInitialize(BA _ba) throws Exception {
        if (ba == null) {
            ba = new BA(_ba, this, htSubs, "b4a.example.slidingpanels");
            if (htSubs == null) {
                ba.loadHtSubs(this.getClass());
                htSubs = ba.htSubs;
            }
            
        }
        if (BA.isShellModeRuntimeCheck(ba)) 
			   this.getClass().getMethod("_class_globals", b4a.example.slidingpanels.class).invoke(this, new Object[] {null});
        else
            ba.raiseEvent2(null, true, "class_globals", false);
    }

 public anywheresoftware.b4a.keywords.Common __c = null;
public float _usefriction = 0f;
public float _friction_dec = 0f;
public float _friction_inc = 0f;
public float _friction_accelerate = 0f;
public int _action_down = 0;
public int _action_up = 0;
public int _action_move = 0;
public int _disxtest = 0;
public int _veltest = 0;
public int _marginetouch = 0;
public anywheresoftware.b4a.objects.Timer _timer1 = null;
public anywheresoftware.b4a.objects.Timer _timerlc = null;
public anywheresoftware.b4a.objects.PanelWrapper _display = null;
public int _x0 = 0;
public int _x1 = 0;
public int _currentpanel = 0;
public int _velocity = 0;
public int _vdistance = 0;
public int _touched = 0;
public int _vwidth = 0;
public int _vypos = 0;
public int _vzoom = 0;
public int _vzoomarea = 0;
public int _origh = 0;
public int _origw = 0;
public boolean _noloop = false;
public boolean _firsttime = false;
public boolean _vfriction = false;
public boolean _slidinginprogress = false;
public boolean _vactivitytouch = false;
public boolean _longclick = false;
public long _rapidsliding = 0L;
public String _veventname = "";
public String _eventtouch = "";
public b4a.example.slidingpanels._jumpdata _jump = null;
public b4a.example.slidingpanels._movespanel _move = null;
public Object _vmodule = null;
public b4a.example.slidingpanels._touchdata _vtouchdata = null;
public anywheresoftware.b4a.objects.PanelWrapper[] _panels = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.menu _menu = null;
public b4a.example.layer _layer = null;
public b4a.example.citizen _citizen = null;
public b4a.example.building _building = null;
public b4a.example.land _land = null;
public static class _jumpdata{
public boolean IsInitialized;
public int Panel;
public int Delay;
public int Speed;
public void Initialize() {
IsInitialized = true;
Panel = 0;
Delay = 0;
Speed = 0;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _movespanel{
public boolean IsInitialized;
public int PanelNumber;
public float Start;
public int Destination;
public float Increase;
public void Initialize() {
IsInitialized = true;
PanelNumber = 0;
Start = 0f;
Destination = 0;
Increase = 0f;
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public static class _touchdata{
public boolean IsInitialized;
public int X;
public int Y;
public Object Tag;
public void Initialize() {
IsInitialized = true;
X = 0;
Y = 0;
Tag = new Object();
}
@Override
		public String toString() {
			return BA.TypeToString(this, false);
		}}
public int  _calccenterposition(int _panelnumber,int _reference) throws Exception{
 //BA.debugLineNum = 148;BA.debugLine="Private Sub CalcCenterPosition (PanelNumber As Int";
 //BA.debugLineNum = 149;BA.debugLine="Return (PanelNumber-Reference)*(vWidth+vDistance)";
if (true) return (int) ((_panelnumber-_reference)*(_vwidth+_vdistance)+_panels[_reference].getLeft()+_panels[_reference].getWidth()/(double)2);
 //BA.debugLineNum = 150;BA.debugLine="End Sub";
return 0;
}
public int  _calccurrentpanel() throws Exception{
int _c = 0;
int _tmp = 0;
int _tmpdisx = 0;
int _tmppanel = 0;
 //BA.debugLineNum = 80;BA.debugLine="Private Sub CalcCurrentPanel As Int";
 //BA.debugLineNum = 81;BA.debugLine="Dim c,Tmp,TmpDisX,TmpPanel As Int";
_c = 0;
_tmp = 0;
_tmpdisx = 0;
_tmppanel = 0;
 //BA.debugLineNum = 82;BA.debugLine="TmpDisX = Abs(vWidth/2-GetCenterPosition(0))";
_tmpdisx = (int) (__c.Abs(_vwidth/(double)2-_getcenterposition((int) (0))));
 //BA.debugLineNum = 83;BA.debugLine="If Panels.Length > 1 Then";
if (_panels.length>1) { 
 //BA.debugLineNum = 84;BA.debugLine="For c = 1 To Panels.Length-1";
{
final int step4 = 1;
final int limit4 = (int) (_panels.length-1);
_c = (int) (1) ;
for (;_c <= limit4 ;_c = _c + step4 ) {
 //BA.debugLineNum = 85;BA.debugLine="Tmp = Abs(vWidth/2-GetCenterPosition(c))";
_tmp = (int) (__c.Abs(_vwidth/(double)2-_getcenterposition(_c)));
 //BA.debugLineNum = 86;BA.debugLine="If Tmp < TmpDisX Then";
if (_tmp<_tmpdisx) { 
 //BA.debugLineNum = 87;BA.debugLine="TmpDisX = Tmp";
_tmpdisx = _tmp;
 //BA.debugLineNum = 88;BA.debugLine="TmpPanel = c";
_tmppanel = _c;
 };
 }
};
 };
 //BA.debugLineNum = 92;BA.debugLine="CurrentPanel = TmpPanel";
_currentpanel = _tmppanel;
 //BA.debugLineNum = 93;BA.debugLine="Return CurrentPanel";
if (true) return _currentpanel;
 //BA.debugLineNum = 94;BA.debugLine="End Sub";
return 0;
}
public String  _calculateszoom() throws Exception{
int _c = 0;
int _tmpzoom = 0;
int _topzoom = 0;
int _disp = 0;
 //BA.debugLineNum = 120;BA.debugLine="Private Sub CalculatesZoom";
 //BA.debugLineNum = 121;BA.debugLine="Dim c,tmpZoom,topZoom,DisP As Int";
_c = 0;
_tmpzoom = 0;
_topzoom = 0;
_disp = 0;
 //BA.debugLineNum = 122;BA.debugLine="For c = 0 To Panels.Length-1";
{
final int step2 = 1;
final int limit2 = (int) (_panels.length-1);
_c = (int) (0) ;
for (;_c <= limit2 ;_c = _c + step2 ) {
 //BA.debugLineNum = 123;BA.debugLine="DisP = Min(Abs(Panels(c).Left+Panels(c).Width/2-";
_disp = (int) (__c.Min(__c.Abs(_panels[_c].getLeft()+_panels[_c].getWidth()/(double)2-_display.getWidth()/(double)2),_vzoomarea/(double)2));
 //BA.debugLineNum = 124;BA.debugLine="DisP = (vZoom-100)/(vZoomArea/2)*DisP";
_disp = (int) ((_vzoom-100)/(double)(_vzoomarea/(double)2)*_disp);
 //BA.debugLineNum = 125;BA.debugLine="tmpZoom = vZoom-DisP";
_tmpzoom = (int) (_vzoom-_disp);
 //BA.debugLineNum = 126;BA.debugLine="If tmpZoom > topZoom Then";
if (_tmpzoom>_topzoom) { 
 //BA.debugLineNum = 127;BA.debugLine="topZoom = tmpZoom";
_topzoom = _tmpzoom;
 //BA.debugLineNum = 128;BA.debugLine="Panels(c).BringToFront";
_panels[_c].BringToFront();
 };
 //BA.debugLineNum = 130;BA.debugLine="ZoomPanel(Panels(c),tmpZoom)";
_zoompanel(_panels[_c],_tmpzoom);
 }
};
 //BA.debugLineNum = 132;BA.debugLine="End Sub";
return "";
}
public String  _class_globals() throws Exception{
 //BA.debugLineNum = 14;BA.debugLine="Private Sub Class_Globals";
 //BA.debugLineNum = 15;BA.debugLine="Private UseFriction,FRICTION_DEC = 0.96,FRICTION_";
_usefriction = 0f;
_friction_dec = (float) (0.96);
_friction_inc = (float) (1.02);
_friction_accelerate = (float) (1.5);
 //BA.debugLineNum = 16;BA.debugLine="Private ACTION_DOWN = 0,ACTION_UP = 1,ACTION_MOVE";
_action_down = (int) (0);
_action_up = (int) (1);
_action_move = (int) (2);
 //BA.debugLineNum = 17;BA.debugLine="Private DisXtest = 160*Density/5,VelTest = 200 As";
_disxtest = (int) (160*__c.Density/(double)5);
_veltest = (int) (200);
 //BA.debugLineNum = 18;BA.debugLine="Private MargineTouch As Int = 10dip";
_marginetouch = __c.DipToCurrent((int) (10));
 //BA.debugLineNum = 20;BA.debugLine="Private Timer1,TimerLC As Timer";
_timer1 = new anywheresoftware.b4a.objects.Timer();
_timerlc = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 21;BA.debugLine="Private Display As Panel";
_display = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private X0,X1,CurrentPanel,Velocity,vDistance,Tou";
_x0 = 0;
_x1 = 0;
_currentpanel = 0;
_velocity = 0;
_vdistance = 0;
_touched = 0;
 //BA.debugLineNum = 23;BA.debugLine="Private vWidth,vYpos,vZoom,vZoomArea,OrigH,OrigW";
_vwidth = 0;
_vypos = 0;
_vzoom = 0;
_vzoomarea = 0;
_origh = 0;
_origw = 0;
 //BA.debugLineNum = 24;BA.debugLine="Private NoLoop,FirstTime,vFriction,SlidingInProgr";
_noloop = false;
_firsttime = false;
_vfriction = false;
_slidinginprogress = false;
_vactivitytouch = false;
_longclick = false;
 //BA.debugLineNum = 25;BA.debugLine="Private RapidSliding As Long";
_rapidsliding = 0L;
 //BA.debugLineNum = 26;BA.debugLine="Private vEventName,EventTouch As String";
_veventname = "";
_eventtouch = "";
 //BA.debugLineNum = 27;BA.debugLine="Type JumpData (Panel As Int,Delay As Int,Speed As";
;
 //BA.debugLineNum = 28;BA.debugLine="Private Jump As JumpData";
_jump = new b4a.example.slidingpanels._jumpdata();
 //BA.debugLineNum = 29;BA.debugLine="Type MovesPanel (PanelNumber As Int,Start As Floa";
;
 //BA.debugLineNum = 30;BA.debugLine="Private Move As MovesPanel";
_move = new b4a.example.slidingpanels._movespanel();
 //BA.debugLineNum = 31;BA.debugLine="Private vModule As Object";
_vmodule = new Object();
 //BA.debugLineNum = 32;BA.debugLine="Type TouchData (X As Int,Y As Int,Tag As Object)";
;
 //BA.debugLineNum = 33;BA.debugLine="Private vTouchData As TouchData";
_vtouchdata = new b4a.example.slidingpanels._touchdata();
 //BA.debugLineNum = 35;BA.debugLine="Public Panels() As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper[(int) (0)];
{
int d0 = _panels.length;
for (int i0 = 0;i0 < d0;i0++) {
_panels[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 36;BA.debugLine="End Sub";
return "";
}
public int  _clickedpanel(int _x,int _y) throws Exception{
int _c = 0;
int _dimension = 0;
int _idx = 0;
 //BA.debugLineNum = 161;BA.debugLine="Private Sub ClickedPanel (X As Int,Y As Int) As In";
 //BA.debugLineNum = 162;BA.debugLine="Dim c,dimension,idx = -1 As Int";
_c = 0;
_dimension = 0;
_idx = (int) (-1);
 //BA.debugLineNum = 163;BA.debugLine="For c = 0 To Panels.Length-1";
{
final int step2 = 1;
final int limit2 = (int) (_panels.length-1);
_c = (int) (0) ;
for (;_c <= limit2 ;_c = _c + step2 ) {
 //BA.debugLineNum = 164;BA.debugLine="If X >= Panels(c).Left AND Y >= Panels(c).Top AN";
if (_x>=_panels[_c].getLeft() && _y>=_panels[_c].getTop() && _x<=_panels[_c].getLeft()+_panels[_c].getWidth() && _y<=_panels[_c].getTop()+_panels[_c].getHeight()) { 
 //BA.debugLineNum = 166;BA.debugLine="If Panels(c).Width > dimension Then";
if (_panels[_c].getWidth()>_dimension) { 
 //BA.debugLineNum = 167;BA.debugLine="dimension = Panels(c).Width";
_dimension = _panels[_c].getWidth();
 //BA.debugLineNum = 168;BA.debugLine="idx = c";
_idx = _c;
 };
 };
 }
};
 //BA.debugLineNum = 172;BA.debugLine="Return idx";
if (true) return _idx;
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return 0;
}
public String  _concatenates(int _panelnumber) throws Exception{
int _c = 0;
int _dist = 0;
 //BA.debugLineNum = 96;BA.debugLine="Private Sub Concatenates (PanelNumber As Int)";
 //BA.debugLineNum = 97;BA.debugLine="Dim c,dist As Int";
_c = 0;
_dist = 0;
 //BA.debugLineNum = 98;BA.debugLine="For c = 0 To Panels.Length-1";
{
final int step2 = 1;
final int limit2 = (int) (_panels.length-1);
_c = (int) (0) ;
for (;_c <= limit2 ;_c = _c + step2 ) {
 //BA.debugLineNum = 99;BA.debugLine="If c <> PanelNumber Then";
if (_c!=_panelnumber) { 
 //BA.debugLineNum = 100;BA.debugLine="dist = CalcCenterPosition(c,PanelNumber)";
_dist = _calccenterposition(_c,_panelnumber);
 //BA.debugLineNum = 101;BA.debugLine="If dist-Panels(c).Width/2 < Display.Width OR di";
if (_dist-_panels[_c].getWidth()/(double)2<_display.getWidth() || _dist+_panels[_c].getWidth()/(double)2>0) { 
 //BA.debugLineNum = 102;BA.debugLine="SetLeftPosition(c,dist)";
_setleftposition(_c,_dist);
 };
 };
 }
};
 //BA.debugLineNum = 106;BA.debugLine="If vZoom > 0 Then CalculatesZoom";
if (_vzoom>0) { 
_calculateszoom();};
 //BA.debugLineNum = 107;BA.debugLine="If NoLoop Then";
if (_noloop) { 
 //BA.debugLineNum = 108;BA.debugLine="If CalcCenterPosition(0,PanelNumber) > Display.W";
if (_calccenterposition((int) (0),_panelnumber)>_display.getWidth()+_vwidth || _calccenterposition((int) (_panels.length-1),_panelnumber)<-_vwidth) { 
_frictionpanelback();};
 }else if(_firsttime==__c.False) { 
 //BA.debugLineNum = 111;BA.debugLine="If PanelNumber = 0 AND Panels(PanelNumber).Left";
if (_panelnumber==0 && _panels[_panelnumber].getLeft()>0) { 
 //BA.debugLineNum = 112;BA.debugLine="Panels(Panels.Length-1).Left = Panels(PanelNumb";
_panels[(int) (_panels.length-1)].setLeft((int) (_panels[_panelnumber].getLeft()-_panels[_panelnumber].getWidth()));
 }else if(_panelnumber==_panels.length-1 && _panels[_panelnumber].getLeft()<0) { 
 //BA.debugLineNum = 114;BA.debugLine="Panels(0).Left = Panels(PanelNumber).Left+Panel";
_panels[(int) (0)].setLeft((int) (_panels[_panelnumber].getLeft()+_panels[_panelnumber].getWidth()));
 };
 };
 //BA.debugLineNum = 117;BA.debugLine="Display.Invalidate";
_display.Invalidate();
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public String  _frictionpanelback() throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Private Sub FrictionPanelBack";
 //BA.debugLineNum = 76;BA.debugLine="UseFriction = FRICTION_INC";
_usefriction = _friction_inc;
 //BA.debugLineNum = 77;BA.debugLine="PanelToCentre(CalcCurrentPanel,Velocity*5) '*10)";
_paneltocentre(_calccurrentpanel(),(int) (_velocity*5));
 //BA.debugLineNum = 78;BA.debugLine="End Sub";
return "";
}
public int  _getcenterposition(int _panelnumber) throws Exception{
 //BA.debugLineNum = 152;BA.debugLine="Private Sub GetCenterPosition (PanelNumber As Int)";
 //BA.debugLineNum = 153;BA.debugLine="PanelNumber = Min(Max(0,PanelNumber),Panels.Lengt";
_panelnumber = (int) (__c.Min(__c.Max(0,_panelnumber),_panels.length-1));
 //BA.debugLineNum = 154;BA.debugLine="Return Panels(PanelNumber).Width/2+Panels(PanelNu";
if (true) return (int) (_panels[_panelnumber].getWidth()/(double)2+_panels[_panelnumber].getLeft());
 //BA.debugLineNum = 155;BA.debugLine="End Sub";
return 0;
}
public String  _getcurrentpanel() throws Exception{
 //BA.debugLineNum = 331;BA.debugLine="Public Sub GetCurrentPanel";
 //BA.debugLineNum = 332;BA.debugLine="Return CurrentPanel";
if (true) return BA.NumberToString(_currentpanel);
 //BA.debugLineNum = 333;BA.debugLine="End Sub";
return "";
}
public String  _getslidinginprogress() throws Exception{
 //BA.debugLineNum = 336;BA.debugLine="Public Sub GetSlidingInProgress";
 //BA.debugLineNum = 337;BA.debugLine="Return SlidingInProgress";
if (true) return BA.ObjectToString(_slidinginprogress);
 //BA.debugLineNum = 338;BA.debugLine="End Sub";
return "";
}
public String  _initialize(anywheresoftware.b4a.BA _ba,String _eventname,int _speed,anywheresoftware.b4a.objects.PanelWrapper _parent,Object _module,boolean _activitytouch) throws Exception{
innerInitialize(_ba);
 //BA.debugLineNum = 376;BA.debugLine="Public Sub Initialize (EventName As String,Speed A";
 //BA.debugLineNum = 377;BA.debugLine="vEventName = EventName";
_veventname = _eventname;
 //BA.debugLineNum = 378;BA.debugLine="Velocity = Speed";
_velocity = _speed;
 //BA.debugLineNum = 379;BA.debugLine="Display = Parent";
_display = _parent;
 //BA.debugLineNum = 380;BA.debugLine="vModule = Module";
_vmodule = _module;
 //BA.debugLineNum = 381;BA.debugLine="FirstTime = True";
_firsttime = __c.True;
 //BA.debugLineNum = 382;BA.debugLine="NoLoop = True";
_noloop = __c.True;
 //BA.debugLineNum = 383;BA.debugLine="vActivityTouch = ActivityTouch";
_vactivitytouch = _activitytouch;
 //BA.debugLineNum = 384;BA.debugLine="If vActivityTouch = False Then EventTouch = \"Pane";
if (_vactivitytouch==__c.False) { 
_eventtouch = "Panels";};
 //BA.debugLineNum = 385;BA.debugLine="Jump.Panel = -1";
_jump.Panel = (int) (-1);
 //BA.debugLineNum = 386;BA.debugLine="Timer1.Initialize(\"Timer\",15)";
_timer1.Initialize(ba,"Timer",(long) (15));
 //BA.debugLineNum = 387;BA.debugLine="TimerLC.Initialize(\"TimerLC\",500)";
_timerlc.Initialize(ba,"TimerLC",(long) (500));
 //BA.debugLineNum = 388;BA.debugLine="End Sub";
return "";
}
public boolean  _jumptopanel(int _panelnumber,int _speed,int _delay) throws Exception{
int _nextpanel = 0;
 //BA.debugLineNum = 309;BA.debugLine="Public Sub JumpToPanel (PanelNumber As Int,Speed A";
 //BA.debugLineNum = 310;BA.debugLine="PanelNumber = Max(Min(PanelNumber,Panels.Length-1";
_panelnumber = (int) (__c.Max(__c.Min(_panelnumber,_panels.length-1),0));
 //BA.debugLineNum = 311;BA.debugLine="Jump.Panel = PanelNumber";
_jump.Panel = _panelnumber;
 //BA.debugLineNum = 312;BA.debugLine="If PanelNumber = CurrentPanel Then";
if (_panelnumber==_currentpanel) { 
 //BA.debugLineNum = 313;BA.debugLine="Jump.Panel = -1";
_jump.Panel = (int) (-1);
 //BA.debugLineNum = 314;BA.debugLine="Return False";
if (true) return __c.False;
 };
 //BA.debugLineNum = 316;BA.debugLine="If SlidingInProgress Then SlidingInProgress = Fal";
if (_slidinginprogress) { 
_slidinginprogress = __c.False;};
 //BA.debugLineNum = 317;BA.debugLine="Jump.Delay = Delay";
_jump.Delay = _delay;
 //BA.debugLineNum = 318;BA.debugLine="Jump.Speed = Speed";
_jump.Speed = _speed;
 //BA.debugLineNum = 319;BA.debugLine="Wait(Delay)";
_wait(_delay);
 //BA.debugLineNum = 320;BA.debugLine="Dim NextPanel As Int";
_nextpanel = 0;
 //BA.debugLineNum = 321;BA.debugLine="If PanelNumber < CurrentPanel Then '---Right dire";
if (_panelnumber<_currentpanel) { 
 //BA.debugLineNum = 322;BA.debugLine="NextPanel = CurrentPanel - 1";
_nextpanel = (int) (_currentpanel-1);
 }else {
 //BA.debugLineNum = 324;BA.debugLine="NextPanel = CurrentPanel + 1";
_nextpanel = (int) (_currentpanel+1);
 };
 //BA.debugLineNum = 326;BA.debugLine="PanelToCentre(NextPanel,Jump.Speed)";
_paneltocentre(_nextpanel,_jump.Speed);
 //BA.debugLineNum = 327;BA.debugLine="Return True";
if (true) return __c.True;
 //BA.debugLineNum = 328;BA.debugLine="End Sub";
return false;
}
public String  _modefullscreen(int _numberofpanels,boolean _slidinginloop) throws Exception{
int _c = 0;
 //BA.debugLineNum = 394;BA.debugLine="Public Sub ModeFullScreen (NumberOfPanels As Int,S";
 //BA.debugLineNum = 395;BA.debugLine="Dim c As Int";
_c = 0;
 //BA.debugLineNum = 396;BA.debugLine="Dim Panels(Max(NumberOfPanels,2)) As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper[(int) (__c.Max(_numberofpanels,2))];
{
int d0 = _panels.length;
for (int i0 = 0;i0 < d0;i0++) {
_panels[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 397;BA.debugLine="vYpos = Display.Height/2";
_vypos = (int) (_display.getHeight()/(double)2);
 //BA.debugLineNum = 398;BA.debugLine="vWidth = Display.Width";
_vwidth = _display.getWidth();
 //BA.debugLineNum = 399;BA.debugLine="For c = 0 To Panels.Length-1";
{
final int step5 = 1;
final int limit5 = (int) (_panels.length-1);
_c = (int) (0) ;
for (;_c <= limit5 ;_c = _c + step5 ) {
 //BA.debugLineNum = 400;BA.debugLine="Panels(c).Initialize(EventTouch)";
_panels[_c].Initialize(ba,_eventtouch);
 //BA.debugLineNum = 401;BA.debugLine="Panels(c).Tag = c";
_panels[_c].setTag((Object)(_c));
 //BA.debugLineNum = 402;BA.debugLine="Display.AddView(Panels(c),vWidth,0,vWidth,Displa";
_display.AddView((android.view.View)(_panels[_c].getObject()),_vwidth,(int) (0),_vwidth,_display.getHeight());
 }
};
 //BA.debugLineNum = 404;BA.debugLine="NoLoop = Not(SlidingInLoop)";
_noloop = __c.Not(_slidinginloop);
 //BA.debugLineNum = 405;BA.debugLine="vDistance = 0";
_vdistance = (int) (0);
 //BA.debugLineNum = 406;BA.debugLine="vFriction = False";
_vfriction = __c.False;
 //BA.debugLineNum = 407;BA.debugLine="End Sub";
return "";
}
public String  _modelittlepanels(int _numberofpanels,int _width,int _height,int _ypos,int _distance,boolean _friction) throws Exception{
int _c = 0;
 //BA.debugLineNum = 417;BA.debugLine="Public Sub ModeLittlePanels (NumberOfPanels As Int";
 //BA.debugLineNum = 418;BA.debugLine="Dim c As Int";
_c = 0;
 //BA.debugLineNum = 419;BA.debugLine="Dim Panels(Max(NumberOfPanels,2)) As Panel";
_panels = new anywheresoftware.b4a.objects.PanelWrapper[(int) (__c.Max(_numberofpanels,2))];
{
int d0 = _panels.length;
for (int i0 = 0;i0 < d0;i0++) {
_panels[i0] = new anywheresoftware.b4a.objects.PanelWrapper();
}
}
;
 //BA.debugLineNum = 420;BA.debugLine="vWidth = Min(Width,Display.Width)";
_vwidth = (int) (__c.Min(_width,_display.getWidth()));
 //BA.debugLineNum = 421;BA.debugLine="vYpos = Ypos";
_vypos = _ypos;
 //BA.debugLineNum = 422;BA.debugLine="Height = Min(Height,Display.Height)";
_height = (int) (__c.Min(_height,_display.getHeight()));
 //BA.debugLineNum = 423;BA.debugLine="For c = 0 To Panels.Length-1";
{
final int step6 = 1;
final int limit6 = (int) (_panels.length-1);
_c = (int) (0) ;
for (;_c <= limit6 ;_c = _c + step6 ) {
 //BA.debugLineNum = 424;BA.debugLine="Panels(c).Initialize(EventTouch)";
_panels[_c].Initialize(ba,_eventtouch);
 //BA.debugLineNum = 425;BA.debugLine="Panels(c).Tag = c";
_panels[_c].setTag((Object)(_c));
 //BA.debugLineNum = 426;BA.debugLine="Display.AddView(Panels(c),Display.Width,vYpos-(H";
_display.AddView((android.view.View)(_panels[_c].getObject()),_display.getWidth(),(int) (_vypos-(_height/(double)2)),_vwidth,_height);
 }
};
 //BA.debugLineNum = 428;BA.debugLine="vDistance = Distance";
_vdistance = _distance;
 //BA.debugLineNum = 429;BA.debugLine="vFriction = Friction";
_vfriction = _friction;
 //BA.debugLineNum = 430;BA.debugLine="End Sub";
return "";
}
public String  _modelittlepanelszoom(int _numberofpanels,int _width,int _height,int _ypos,int _distance,boolean _friction,int _zoom,int _zoomarea) throws Exception{
 //BA.debugLineNum = 442;BA.debugLine="Public Sub ModeLittlePanelsZoom (NumberOfPanels As";
 //BA.debugLineNum = 443;BA.debugLine="ModeLittlePanels(NumberOfPanels,Width,Height,Ypos";
_modelittlepanels(_numberofpanels,_width,_height,_ypos,_distance,_friction);
 //BA.debugLineNum = 444;BA.debugLine="OrigW = vWidth";
_origw = _vwidth;
 //BA.debugLineNum = 445;BA.debugLine="OrigH = Height";
_origh = _height;
 //BA.debugLineNum = 446;BA.debugLine="vZoom = Zoom";
_vzoom = _zoom;
 //BA.debugLineNum = 447;BA.debugLine="vZoomArea = ZoomArea";
_vzoomarea = _zoomarea;
 //BA.debugLineNum = 448;BA.debugLine="End Sub";
return "";
}
public String  _panels_touch(int _action,float _x,float _y) throws Exception{
anywheresoftware.b4a.objects.PanelWrapper _send = null;
int _disx = 0;
anywheresoftware.b4a.objects.collections.List _lista = null;
long _vel = 0L;
int _nextpanel = 0;
int _returnback = 0;
boolean _testvelocity = false;
 //BA.debugLineNum = 185;BA.debugLine="Public Sub Panels_Touch (Action As Int,X As Float,";
 //BA.debugLineNum = 186;BA.debugLine="If SlidingInProgress Then";
if (_slidinginprogress) { 
 //BA.debugLineNum = 187;BA.debugLine="SlidingInProgress = False";
_slidinginprogress = __c.False;
 //BA.debugLineNum = 188;BA.debugLine="Timer1.Enabled = False";
_timer1.setEnabled(__c.False);
 //BA.debugLineNum = 189;BA.debugLine="Jump.Panel = -1";
_jump.Panel = (int) (-1);
 };
 //BA.debugLineNum = 191;BA.debugLine="Select Action";
switch (BA.switchObjectToInt(_action,_action_down,_action_move,_action_up)) {
case 0: {
 //BA.debugLineNum = 193;BA.debugLine="If vActivityTouch Then";
if (_vactivitytouch) { 
 //BA.debugLineNum = 194;BA.debugLine="Touched = ClickedPanel(X,Y)";
_touched = _clickedpanel((int) (_x),(int) (_y));
 //BA.debugLineNum = 195;BA.debugLine="If Touched = -1 Then";
if (_touched==-1) { 
 //BA.debugLineNum = 196;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 198;BA.debugLine="X = X - Panels(Touched).Left";
_x = (float) (_x-_panels[_touched].getLeft());
 }else {
 //BA.debugLineNum = 200;BA.debugLine="Dim Send  = Sender As Panel";
_send = new anywheresoftware.b4a.objects.PanelWrapper();
_send.setObject((android.view.ViewGroup)(__c.Sender(ba)));
 //BA.debugLineNum = 201;BA.debugLine="Touched = Send.Tag";
_touched = (int)(BA.ObjectToNumber(_send.getTag()));
 };
 //BA.debugLineNum = 203;BA.debugLine="RapidSliding = DateTime.Now";
_rapidsliding = __c.DateTime.getNow();
 //BA.debugLineNum = 204;BA.debugLine="X0 = X";
_x0 = (int) (_x);
 //BA.debugLineNum = 205;BA.debugLine="X1 = GetCenterPosition(Touched)";
_x1 = _getcenterposition(_touched);
 //BA.debugLineNum = 207;BA.debugLine="vTouchData.X = X";
_vtouchdata.X = (int) (_x);
 //BA.debugLineNum = 208;BA.debugLine="vTouchData.Y = Y";
_vtouchdata.Y = (int) (_y);
 //BA.debugLineNum = 209;BA.debugLine="LongClick = False";
_longclick = __c.False;
 //BA.debugLineNum = 210;BA.debugLine="TimerLC.Enabled = True";
_timerlc.setEnabled(__c.True);
 break; }
case 1: {
 //BA.debugLineNum = 212;BA.debugLine="If Touched = -1 Then Return";
if (_touched==-1) { 
if (true) return "";};
 //BA.debugLineNum = 213;BA.debugLine="If vActivityTouch Then X = X - Panels(Touched).";
if (_vactivitytouch) { 
_x = (float) (_x-_panels[_touched].getLeft());};
 //BA.debugLineNum = 214;BA.debugLine="If Abs(vTouchData.X-X) > MargineTouch OR Abs(vT";
if (__c.Abs(_vtouchdata.X-_x)>_marginetouch || __c.Abs(_vtouchdata.Y-_y)>_marginetouch) { 
_timerlc.setEnabled(__c.False);};
 //BA.debugLineNum = 215;BA.debugLine="SetLeftPosition(Touched,X-X0+GetCenterPosition(";
_setleftposition(_touched,(int) (_x-_x0+_getcenterposition(_touched)));
 //BA.debugLineNum = 216;BA.debugLine="Concatenates(Touched)";
_concatenates(_touched);
 //BA.debugLineNum = 217;BA.debugLine="If DateTime.Now-RapidSliding > 1000 Then";
if (__c.DateTime.getNow()-_rapidsliding>1000) { 
 //BA.debugLineNum = 218;BA.debugLine="RapidSliding = DateTime.Now";
_rapidsliding = __c.DateTime.getNow();
 };
 break; }
case 2: {
 //BA.debugLineNum = 221;BA.debugLine="TimerLC.Enabled = False";
_timerlc.setEnabled(__c.False);
 //BA.debugLineNum = 222;BA.debugLine="If Touched = -1 Then Touched = CurrentPanel";
if (_touched==-1) { 
_touched = _currentpanel;};
 //BA.debugLineNum = 223;BA.debugLine="Dim DisX = GetCenterPosition(Touched)-X1 As Int";
_disx = (int) (_getcenterposition(_touched)-_x1);
 //BA.debugLineNum = 225;BA.debugLine="If Abs(DisX) < MargineTouch  Then";
if (__c.Abs(_disx)<_marginetouch) { 
 //BA.debugLineNum = 226;BA.debugLine="If SubExists(vModule,vEventName&\"_Click\") AND";
if (__c.SubExists(ba,_vmodule,_veventname+"_Click") && _longclick==__c.False) { 
 //BA.debugLineNum = 227;BA.debugLine="Dim lista As List";
_lista = new anywheresoftware.b4a.objects.collections.List();
 //BA.debugLineNum = 228;BA.debugLine="If vActivityTouch Then";
if (_vactivitytouch) { 
 //BA.debugLineNum = 229;BA.debugLine="X = X - Panels(Touched).Left";
_x = (float) (_x-_panels[_touched].getLeft());
 //BA.debugLineNum = 230;BA.debugLine="Y = Y - Panels(Touched).Top";
_y = (float) (_y-_panels[_touched].getTop());
 };
 //BA.debugLineNum = 232;BA.debugLine="vTouchData.X = X";
_vtouchdata.X = (int) (_x);
 //BA.debugLineNum = 233;BA.debugLine="vTouchData.Y = Y";
_vtouchdata.Y = (int) (_y);
 //BA.debugLineNum = 234;BA.debugLine="vTouchData.Tag = Panels(Touched).Tag";
_vtouchdata.Tag = _panels[_touched].getTag();
 //BA.debugLineNum = 235;BA.debugLine="FrictionPanelBack";
_frictionpanelback();
 //BA.debugLineNum = 236;BA.debugLine="CallSub2(vModule,vEventName&\"_Click\",vTouchDa";
__c.CallSubNew2(ba,_vmodule,_veventname+"_Click",(Object)(_vtouchdata));
 };
 //BA.debugLineNum = 238;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 241;BA.debugLine="Dim Vel = DateTime.Now-RapidSliding As  Long";
_vel = (long) (__c.DateTime.getNow()-_rapidsliding);
 //BA.debugLineNum = 242;BA.debugLine="If vFriction Then";
if (_vfriction) { 
 //BA.debugLineNum = 243;BA.debugLine="Move.PanelNumber = Touched";
_move.PanelNumber = _touched;
 //BA.debugLineNum = 244;BA.debugLine="Move.Start = CalcCenterPosition(Touched,Curren";
_move.Start = (float) (_calccenterposition(_touched,_currentpanel));
 //BA.debugLineNum = 245;BA.debugLine="If DisX > 0 Then '---------Right direction";
if (_disx>0) { 
 //BA.debugLineNum = 246;BA.debugLine="Move.Destination = Touched*(vWidth+vDistance)";
_move.Destination = (int) (_touched*(_vwidth+_vdistance)+_display.getWidth()+_vwidth);
 }else if(_disx<0) { 
 //BA.debugLineNum = 248;BA.debugLine="Move.Destination = (Panels.Length-1-Touched)*";
_move.Destination = (int) ((_panels.length-1-_touched)*(_vwidth+_vdistance)-_vwidth);
 };
 //BA.debugLineNum = 250;BA.debugLine="Move.Increase = DisX/(Vel/Timer1.Interval)*FRI";
_move.Increase = (float) (_disx/(double)(_vel/(double)_timer1.getInterval())*_friction_accelerate);
 //BA.debugLineNum = 251;BA.debugLine="UseFriction = FRICTION_DEC";
_usefriction = _friction_dec;
 //BA.debugLineNum = 252;BA.debugLine="SlidingInProgress = True";
_slidinginprogress = __c.True;
 //BA.debugLineNum = 253;BA.debugLine="Timer1.Enabled = True";
_timer1.setEnabled(__c.True);
 //BA.debugLineNum = 254;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 257;BA.debugLine="Dim NextPanel,ReturnBack As Int";
_nextpanel = 0;
_returnback = 0;
 //BA.debugLineNum = 258;BA.debugLine="If DisX > 0 Then '---------Right direction";
if (_disx>0) { 
 //BA.debugLineNum = 259;BA.debugLine="NextPanel = CurrentPanel - 1";
_nextpanel = (int) (_currentpanel-1);
 //BA.debugLineNum = 260;BA.debugLine="ReturnBack = 0";
_returnback = (int) (0);
 }else if(_disx<0) { 
 //BA.debugLineNum = 262;BA.debugLine="NextPanel = CurrentPanel + 1";
_nextpanel = (int) (_currentpanel+1);
 //BA.debugLineNum = 263;BA.debugLine="ReturnBack = Panels.Length-1";
_returnback = (int) (_panels.length-1);
 };
 //BA.debugLineNum = 265;BA.debugLine="If NoLoop AND Touched = ReturnBack Then";
if (_noloop && _touched==_returnback) { 
 //BA.debugLineNum = 266;BA.debugLine="PanelToCentre(Touched,Velocity)";
_paneltocentre(_touched,_velocity);
 //BA.debugLineNum = 267;BA.debugLine="Return";
if (true) return "";
 }else {
 //BA.debugLineNum = 269;BA.debugLine="Dim TestVelocity As Boolean";
_testvelocity = false;
 //BA.debugLineNum = 270;BA.debugLine="DisX = Abs(DisX)";
_disx = (int) (__c.Abs(_disx));
 //BA.debugLineNum = 271;BA.debugLine="If DisX > DisXtest AND Vel < VelTest Then Test";
if (_disx>_disxtest && _vel<_veltest) { 
_testvelocity = __c.True;}
else {
_testvelocity = __c.False;};
 //BA.debugLineNum = 272;BA.debugLine="If Max(GetCenterPosition(Touched),X1)-Min(GetC";
if (__c.Max(_getcenterposition(_touched),_x1)-__c.Min(_getcenterposition(_touched),_x1)>_vwidth/(double)2 || _testvelocity) { 
 //BA.debugLineNum = 273;BA.debugLine="If NextPanel < 0 Then";
if (_nextpanel<0) { 
 //BA.debugLineNum = 274;BA.debugLine="NextPanel = Panels.Length-1";
_nextpanel = (int) (_panels.length-1);
 //BA.debugLineNum = 275;BA.debugLine="CurrentPanel = NextPanel";
_currentpanel = _nextpanel;
 }else if(_nextpanel>_panels.length-1) { 
 //BA.debugLineNum = 277;BA.debugLine="NextPanel = 0";
_nextpanel = (int) (0);
 //BA.debugLineNum = 278;BA.debugLine="CurrentPanel = NextPanel";
_currentpanel = _nextpanel;
 };
 //BA.debugLineNum = 280;BA.debugLine="If TestVelocity Then";
if (_testvelocity) { 
 //BA.debugLineNum = 281;BA.debugLine="Vel = Max(Vel/DisX*Abs(Display.Width/2-Abs(G";
_vel = (long) (__c.Max(_vel/(double)_disx*__c.Abs(_display.getWidth()/(double)2-__c.Abs(_getcenterposition(_touched))),_velocity));
 //BA.debugLineNum = 282;BA.debugLine="PanelToCentre(NextPanel,Vel)";
_paneltocentre(_nextpanel,(int) (_vel));
 }else {
 //BA.debugLineNum = 284;BA.debugLine="PanelToCentre(NextPanel,Velocity) '---Forwar";
_paneltocentre(_nextpanel,_velocity);
 };
 }else {
 //BA.debugLineNum = 287;BA.debugLine="PanelToCentre(Touched,Velocity) '----------Ba";
_paneltocentre(_touched,_velocity);
 };
 };
 break; }
}
;
 //BA.debugLineNum = 291;BA.debugLine="End Sub";
return "";
}
public String  _paneltocentre(int _panelnumber,int _speed) throws Exception{
 //BA.debugLineNum = 134;BA.debugLine="Private Sub PanelToCentre (PanelNumber As Int,Spee";
 //BA.debugLineNum = 135;BA.debugLine="Move.PanelNumber = PanelNumber";
_move.PanelNumber = _panelnumber;
 //BA.debugLineNum = 136;BA.debugLine="Move.Start = CalcCenterPosition(PanelNumber,Curre";
_move.Start = (float) (_calccenterposition(_panelnumber,_currentpanel));
 //BA.debugLineNum = 137;BA.debugLine="Move.Destination = Display.Width/2";
_move.Destination = (int) (_display.getWidth()/(double)2);
 //BA.debugLineNum = 138;BA.debugLine="Speed = Speed/Display.Width*Abs(Move.Destination-";
_speed = (int) (_speed/(double)_display.getWidth()*__c.Abs(_move.Destination-_move.Start));
 //BA.debugLineNum = 139;BA.debugLine="If UseFriction  = FRICTION_DEC Then";
if (_usefriction==_friction_dec) { 
 //BA.debugLineNum = 140;BA.debugLine="Move.Increase = 1";
_move.Increase = (float) (1);
 }else {
 //BA.debugLineNum = 142;BA.debugLine="Move.Increase = (Move.Destination-Move.Start)/(S";
_move.Increase = (float) ((_move.Destination-_move.Start)/(double)(_speed/(double)_timer1.getInterval()));
 };
 //BA.debugLineNum = 144;BA.debugLine="SlidingInProgress = True";
_slidinginprogress = __c.True;
 //BA.debugLineNum = 145;BA.debugLine="Timer1.Enabled = True";
_timer1.setEnabled(__c.True);
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
return "";
}
public String  _setleftposition(int _panelnumber,int _centerposition) throws Exception{
 //BA.debugLineNum = 157;BA.debugLine="Private Sub SetLeftPosition (PanelNumber As Int,Ce";
 //BA.debugLineNum = 158;BA.debugLine="Panels(PanelNumber).Left = CenterPosition-(Panels";
_panels[_panelnumber].setLeft((int) (_centerposition-(_panels[_panelnumber].getWidth()/(double)2)));
 //BA.debugLineNum = 159;BA.debugLine="End Sub";
return "";
}
public String  _setspeedscroll(int _speed) throws Exception{
 //BA.debugLineNum = 342;BA.debugLine="Public Sub SetSpeedScroll (Speed As Int)";
 //BA.debugLineNum = 343;BA.debugLine="Velocity = Speed";
_velocity = _speed;
 //BA.debugLineNum = 344;BA.debugLine="End Sub";
return "";
}
public String  _start(int _panelnumber) throws Exception{
 //BA.debugLineNum = 296;BA.debugLine="Public Sub Start (PanelNumber As Int)";
 //BA.debugLineNum = 297;BA.debugLine="If FirstTime = False Then Return";
if (_firsttime==__c.False) { 
if (true) return "";};
 //BA.debugLineNum = 298;BA.debugLine="PanelNumber = Max(Min(PanelNumber,Panels.Length-1";
_panelnumber = (int) (__c.Max(__c.Min(_panelnumber,_panels.length-1),0));
 //BA.debugLineNum = 299;BA.debugLine="Wait(200)";
_wait((int) (200));
 //BA.debugLineNum = 300;BA.debugLine="PanelToCentre(PanelNumber,Velocity)";
_paneltocentre(_panelnumber,_velocity);
 //BA.debugLineNum = 301;BA.debugLine="JumpToPanel(PanelNumber,Velocity,0)";
_jumptopanel(_panelnumber,_velocity,(int) (0));
 //BA.debugLineNum = 302;BA.debugLine="End Sub";
return "";
}
public String  _timer_tick() throws Exception{
int _c = 0;
int _gcp = 0;
 //BA.debugLineNum = 38;BA.debugLine="Private Sub Timer_Tick";
 //BA.debugLineNum = 39;BA.debugLine="If Not(SlidingInProgress) Then";
if (__c.Not(_slidinginprogress)) { 
 //BA.debugLineNum = 40;BA.debugLine="Timer1.Enabled = False";
_timer1.setEnabled(__c.False);
 //BA.debugLineNum = 41;BA.debugLine="Return";
if (true) return "";
 };
 //BA.debugLineNum = 43;BA.debugLine="Dim c = Move.PanelNumber,GCP = GetCenterPosition(";
_c = _move.PanelNumber;
_gcp = _getcenterposition(_c);
 //BA.debugLineNum = 44;BA.debugLine="If Abs(GCP-Move.Destination) < Abs(Move.Increase)";
if (__c.Abs(_gcp-_move.Destination)<__c.Abs(_move.Increase) || _gcp==_move.Destination) { 
 //BA.debugLineNum = 45;BA.debugLine="SetLeftPosition(c,Move.Destination)";
_setleftposition(_c,_move.Destination);
 //BA.debugLineNum = 46;BA.debugLine="CurrentPanel = Move.PanelNumber";
_currentpanel = _move.PanelNumber;
 //BA.debugLineNum = 47;BA.debugLine="SlidingInProgress = False";
_slidinginprogress = __c.False;
 }else if(__c.Abs(_move.Increase)<0.5) { 
 //BA.debugLineNum = 49;BA.debugLine="SlidingInProgress = False";
_slidinginprogress = __c.False;
 }else {
 //BA.debugLineNum = 51;BA.debugLine="If UseFriction = FRICTION_DEC Then";
if (_usefriction==_friction_dec) { 
 //BA.debugLineNum = 52;BA.debugLine="Move.Increase = Move.Increase*FRICTION_DEC";
_move.Increase = (float) (_move.Increase*_friction_dec);
 }else if(_usefriction==_friction_inc) { 
 //BA.debugLineNum = 54;BA.debugLine="Move.Increase = Min(Move.Increase*FRICTION_INC,";
_move.Increase = (float) (__c.Min(_move.Increase*_friction_inc,20));
 };
 //BA.debugLineNum = 56;BA.debugLine="Move.Start = Move.Start + Move.Increase";
_move.Start = (float) (_move.Start+_move.Increase);
 //BA.debugLineNum = 57;BA.debugLine="SetLeftPosition(c,Move.Start)";
_setleftposition(_c,(int) (_move.Start));
 };
 //BA.debugLineNum = 59;BA.debugLine="Concatenates(c)";
_concatenates(_c);
 //BA.debugLineNum = 60;BA.debugLine="If SlidingInProgress = False Then";
if (_slidinginprogress==__c.False) { 
 //BA.debugLineNum = 61;BA.debugLine="If UseFriction = FRICTION_INC Then";
if (_usefriction==_friction_inc) { 
 //BA.debugLineNum = 62;BA.debugLine="UseFriction = 0";
_usefriction = (float) (0);
 //BA.debugLineNum = 63;BA.debugLine="If SubExists(vModule,vEventName&\"_Change\") Then";
if (__c.SubExists(ba,_vmodule,_veventname+"_Change")) { 
__c.CallSubNew2(ba,_vmodule,_veventname+"_Change",(Object)(_currentpanel));};
 }else if(_usefriction==_friction_dec) { 
 //BA.debugLineNum = 65;BA.debugLine="FrictionPanelBack";
_frictionpanelback();
 }else if(_jump.Panel==-1 && _firsttime==__c.False) { 
 //BA.debugLineNum = 67;BA.debugLine="If SubExists(vModule,vEventName&\"_Change\") Then";
if (__c.SubExists(ba,_vmodule,_veventname+"_Change")) { 
__c.CallSubNew2(ba,_vmodule,_veventname+"_Change",(Object)(_currentpanel));};
 }else {
 //BA.debugLineNum = 69;BA.debugLine="FirstTime = False";
_firsttime = __c.False;
 };
 //BA.debugLineNum = 71;BA.debugLine="If Jump.Panel > -1 Then JumpToPanel(Jump.Panel,J";
if (_jump.Panel>-1) { 
_jumptopanel(_jump.Panel,_jump.Speed,_jump.Delay);};
 };
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public String  _timerlc_tick() throws Exception{
int _margine = 0;
 //BA.debugLineNum = 175;BA.debugLine="Private Sub TimerLC_Tick";
 //BA.debugLineNum = 176;BA.debugLine="Dim margine = 5dip As Int";
_margine = __c.DipToCurrent((int) (5));
 //BA.debugLineNum = 177;BA.debugLine="TimerLC.Enabled = False";
_timerlc.setEnabled(__c.False);
 //BA.debugLineNum = 178;BA.debugLine="If SubExists(vModule,vEventName&\"_LongClick\") The";
if (__c.SubExists(ba,_vmodule,_veventname+"_LongClick")) { 
 //BA.debugLineNum = 179;BA.debugLine="vTouchData.Tag = Panels(Touched).Tag";
_vtouchdata.Tag = _panels[_touched].getTag();
 //BA.debugLineNum = 180;BA.debugLine="CallSub2(vModule,vEventName&\"_LongClick\",vTouchD";
__c.CallSubNew2(ba,_vmodule,_veventname+"_LongClick",(Object)(_vtouchdata));
 //BA.debugLineNum = 181;BA.debugLine="LongClick = True";
_longclick = __c.True;
 };
 //BA.debugLineNum = 183;BA.debugLine="End Sub";
return "";
}
public String  _wait(int _milliseconds) throws Exception{
long _time = 0L;
 //BA.debugLineNum = 356;BA.debugLine="Private Sub Wait(Milliseconds As Int)";
 //BA.debugLineNum = 357;BA.debugLine="Dim Time As Long";
_time = 0L;
 //BA.debugLineNum = 358;BA.debugLine="Time = DateTime.Now + (Milliseconds)";
_time = (long) (__c.DateTime.getNow()+(_milliseconds));
 //BA.debugLineNum = 359;BA.debugLine="Do While DateTime.Now < Time";
while (__c.DateTime.getNow()<_time) {
 //BA.debugLineNum = 360;BA.debugLine="DoEvents";
__c.DoEvents();
 }
;
 //BA.debugLineNum = 362;BA.debugLine="End Sub";
return "";
}
public String  _zoompanel(anywheresoftware.b4a.objects.PanelWrapper _obj,int _newzoom) throws Exception{
int _swidth = 0;
int _sheight = 0;
int _left = 0;
int _top = 0;
 //BA.debugLineNum = 346;BA.debugLine="Private Sub ZoomPanel (obj As Panel,NewZoom As Int";
 //BA.debugLineNum = 347;BA.debugLine="Dim sWidth,sHeight As Int";
_swidth = 0;
_sheight = 0;
 //BA.debugLineNum = 348;BA.debugLine="sWidth = OrigW/100*NewZoom";
_swidth = (int) (_origw/(double)100*_newzoom);
 //BA.debugLineNum = 349;BA.debugLine="sHeight = OrigH/OrigW*sWidth";
_sheight = (int) (_origh/(double)_origw*_swidth);
 //BA.debugLineNum = 350;BA.debugLine="Dim Left,Top As Int";
_left = 0;
_top = 0;
 //BA.debugLineNum = 351;BA.debugLine="Left = obj.Left+obj.Width/2-sWidth/2";
_left = (int) (_obj.getLeft()+_obj.getWidth()/(double)2-_swidth/(double)2);
 //BA.debugLineNum = 352;BA.debugLine="Top = vYpos-sHeight/2";
_top = (int) (_vypos-_sheight/(double)2);
 //BA.debugLineNum = 353;BA.debugLine="obj.SetLayout(Left,Top,sWidth,sHeight)";
_obj.SetLayout(_left,_top,_swidth,_sheight);
 //BA.debugLineNum = 354;BA.debugLine="End Sub";
return "";
}
public Object callSub(String sub, Object sender, Object[] args) throws Exception {
BA.senderHolder.set(sender);
return BA.SubDelegator.SubNotFound;
}
}
