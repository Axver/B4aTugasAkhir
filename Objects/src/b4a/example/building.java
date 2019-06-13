package b4a.example;


import anywheresoftware.b4a.B4AMenuItem;
import android.app.Activity;
import android.os.Bundle;
import anywheresoftware.b4a.BA;
import anywheresoftware.b4a.BALayout;
import anywheresoftware.b4a.B4AActivity;
import anywheresoftware.b4a.ObjectWrapper;
import anywheresoftware.b4a.objects.ActivityWrapper;
import java.lang.reflect.InvocationTargetException;
import anywheresoftware.b4a.B4AUncaughtException;
import anywheresoftware.b4a.debug.*;
import java.lang.ref.WeakReference;

public class building extends Activity implements B4AActivity{
	public static building mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = true;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.building");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (building).");
				p.finish();
			}
		}
        processBA.setActivityPaused(true);
        processBA.runHook("oncreate", this, null);
		if (!includeTitle) {
        	this.getWindow().requestFeature(android.view.Window.FEATURE_NO_TITLE);
        }
        if (fullScreen) {
        	getWindow().setFlags(android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN,   
        			android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
		
        processBA.sharedProcessBA.activityBA = null;
		layout = new BALayout(this);
		setContentView(layout);
		afterFirstLayout = false;
        WaitForLayout wl = new WaitForLayout();
        if (anywheresoftware.b4a.objects.ServiceHelper.StarterHelper.startFromActivity(processBA, wl, false))
		    BA.handler.postDelayed(wl, 5);

	}
	static class WaitForLayout implements Runnable {
		public void run() {
			if (afterFirstLayout)
				return;
			if (mostCurrent == null)
				return;
            
			if (mostCurrent.layout.getWidth() == 0) {
				BA.handler.postDelayed(this, 5);
				return;
			}
			mostCurrent.layout.getLayoutParams().height = mostCurrent.layout.getHeight();
			mostCurrent.layout.getLayoutParams().width = mostCurrent.layout.getWidth();
			afterFirstLayout = true;
			mostCurrent.afterFirstLayout();
		}
	}
	private void afterFirstLayout() {
        if (this != mostCurrent)
			return;
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.building");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.building", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (building) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (building) Resume **");
        processBA.raiseEvent(null, "activity_resume");
        if (android.os.Build.VERSION.SDK_INT >= 11) {
			try {
				android.app.Activity.class.getMethod("invalidateOptionsMenu").invoke(this,(Object[]) null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
	public void addMenuItem(B4AMenuItem item) {
		if (menuItems == null)
			menuItems = new java.util.ArrayList<B4AMenuItem>();
		menuItems.add(item);
	}
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
        try {
            if (processBA.subExists("activity_actionbarhomeclick")) {
                Class.forName("android.app.ActionBar").getMethod("setHomeButtonEnabled", boolean.class).invoke(
                    getClass().getMethod("getActionBar").invoke(this), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (processBA.runHook("oncreateoptionsmenu", this, new Object[] {menu}))
            return true;
		if (menuItems == null)
			return false;
		for (B4AMenuItem bmi : menuItems) {
			android.view.MenuItem mi = menu.add(bmi.title);
			if (bmi.drawable != null)
				mi.setIcon(bmi.drawable);
            if (android.os.Build.VERSION.SDK_INT >= 11) {
				try {
                    if (bmi.addToBar) {
				        android.view.MenuItem.class.getMethod("setShowAsAction", int.class).invoke(mi, 1);
                    }
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mi.setOnMenuItemClickListener(new B4AMenuItemsClickListener(bmi.eventName.toLowerCase(BA.cul)));
		}
        
		return true;
	}   
 @Override
 public boolean onOptionsItemSelected(android.view.MenuItem item) {
    if (item.getItemId() == 16908332) {
        processBA.raiseEvent(null, "activity_actionbarhomeclick");
        return true;
    }
    else
        return super.onOptionsItemSelected(item); 
}
@Override
 public boolean onPrepareOptionsMenu(android.view.Menu menu) {
    super.onPrepareOptionsMenu(menu);
    processBA.runHook("onprepareoptionsmenu", this, new Object[] {menu});
    return true;
    
 }
 protected void onStart() {
    super.onStart();
    processBA.runHook("onstart", this, null);
}
 protected void onStop() {
    super.onStop();
    processBA.runHook("onstop", this, null);
}
    public void onWindowFocusChanged(boolean hasFocus) {
       super.onWindowFocusChanged(hasFocus);
       if (processBA.subExists("activity_windowfocuschanged"))
           processBA.raiseEvent2(null, true, "activity_windowfocuschanged", false, hasFocus);
    }
	private class B4AMenuItemsClickListener implements android.view.MenuItem.OnMenuItemClickListener {
		private final String eventName;
		public B4AMenuItemsClickListener(String eventName) {
			this.eventName = eventName;
		}
		public boolean onMenuItemClick(android.view.MenuItem item) {
			processBA.raiseEventFromUI(item.getTitle(), eventName + "_click");
			return true;
		}
	}
    public static Class<?> getObject() {
		return building.class;
	}
    private Boolean onKeySubExist = null;
    private Boolean onKeyUpSubExist = null;
	@Override
	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeydown", this, new Object[] {keyCode, event}))
            return true;
		if (onKeySubExist == null)
			onKeySubExist = processBA.subExists("activity_keypress");
		if (onKeySubExist) {
			if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK &&
					android.os.Build.VERSION.SDK_INT >= 18) {
				HandleKeyDelayed hk = new HandleKeyDelayed();
				hk.kc = keyCode;
				BA.handler.post(hk);
				return true;
			}
			else {
				boolean res = new HandleKeyDelayed().runDirectly(keyCode);
				if (res)
					return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	private class HandleKeyDelayed implements Runnable {
		int kc;
		public void run() {
			runDirectly(kc);
		}
		public boolean runDirectly(int keyCode) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keypress", false, keyCode);
			if (res == null || res == true) {
                return true;
            }
            else if (keyCode == anywheresoftware.b4a.keywords.constants.KeyCodes.KEYCODE_BACK) {
				finish();
				return true;
			}
            return false;
		}
		
	}
    @Override
	public boolean onKeyUp(int keyCode, android.view.KeyEvent event) {
        if (processBA.runHook("onkeyup", this, new Object[] {keyCode, event}))
            return true;
		if (onKeyUpSubExist == null)
			onKeyUpSubExist = processBA.subExists("activity_keyup");
		if (onKeyUpSubExist) {
			Boolean res =  (Boolean)processBA.raiseEvent2(_activity, false, "activity_keyup", false, keyCode);
			if (res == null || res == true)
				return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	@Override
	public void onNewIntent(android.content.Intent intent) {
        super.onNewIntent(intent);
		this.setIntent(intent);
        processBA.runHook("onnewintent", this, new Object[] {intent});
	}
    @Override 
	public void onPause() {
		super.onPause();
        if (_activity == null)
            return;
        if (this != mostCurrent)
			return;
		anywheresoftware.b4a.Msgbox.dismiss(true);
        BA.LogInfo("** Activity (building) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
        if (mostCurrent != null)
            processBA.raiseEvent2(_activity, true, "activity_pause", false, activityBA.activity.isFinishing());		
        processBA.setActivityPaused(true);
        mostCurrent = null;
        if (!activityBA.activity.isFinishing())
			previousOne = new WeakReference<Activity>(this);
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        processBA.runHook("onpause", this, null);
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		previousOne = null;
        processBA.runHook("ondestroy", this, null);
	}
    @Override 
	public void onResume() {
		super.onResume();
        mostCurrent = this;
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (activityBA != null) { //will be null during activity create (which waits for AfterLayout).
        	ResumeMessage rm = new ResumeMessage(mostCurrent);
        	BA.handler.post(rm);
        }
        processBA.runHook("onresume", this, null);
	}
    private static class ResumeMessage implements Runnable {
    	private final WeakReference<Activity> activity;
    	public ResumeMessage(Activity activity) {
    		this.activity = new WeakReference<Activity>(activity);
    	}
		public void run() {
            building mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (building) Resume **");
            if (mc != mostCurrent)
                return;
		    processBA.raiseEvent(mc._activity, "activity_resume", (Object[])null);
		}
    }
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	      android.content.Intent data) {
		processBA.onActivityResult(requestCode, resultCode, data);
        processBA.runHook("onactivityresult", this, new Object[] {requestCode, resultCode});
	}
	private static void initializeGlobals() {
		processBA.raiseEvent2(null, true, "globals", false, (Object[])null);
	}
    public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
        for (int i = 0;i < permissions.length;i++) {
            Object[] o = new Object[] {permissions[i], grantResults[i] == 0};
            processBA.raiseEventFromDifferentThread(null,null, 0, "activity_permissionresult", true, o);
        }
            
    }

public anywheresoftware.b4a.keywords.Common __c = null;
public anywheresoftware.b4a.objects.PanelWrapper _fakeactionbar = null;
public anywheresoftware.b4a.objects.PanelWrapper _underactionbar = null;
public b4a.example.clsslidingsidebar _panelwithsidebar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmenu = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lvmenu = null;
public anywheresoftware.b4a.objects.LabelWrapper _lblinfo = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public static String _domain = "";
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext1 = null;
public anywheresoftware.b4a.samples.httputils2.httpjob _job2 = null;
public static int _length = 0;
public b4a.example.slidingpanels _sd = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.menu _menu = null;
public b4a.example.layer _layer = null;
public b4a.example.citizen _citizen = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
int _barsize = 0;
int _lightcyan = 0;
 //BA.debugLineNum = 31;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 32;BA.debugLine="Dim BarSize As Int: BarSize = 60dip";
_barsize = 0;
 //BA.debugLineNum = 32;BA.debugLine="Dim BarSize As Int: BarSize = 60dip";
_barsize = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60));
 //BA.debugLineNum = 33;BA.debugLine="FakeActionBar.Initialize(\"\")";
mostCurrent._fakeactionbar.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 34;BA.debugLine="FakeActionBar.Color = Colors.RGB(20, 20, 100) 'Da";
mostCurrent._fakeactionbar.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (20),(int) (20),(int) (100)));
 //BA.debugLineNum = 35;BA.debugLine="Activity.AddView(FakeActionBar, 0, 0, 100%x, BarS";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fakeactionbar.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),_barsize);
 //BA.debugLineNum = 37;BA.debugLine="Dim LightCyan As Int: LightCyan = Colors.RGB(0, 9";
_lightcyan = 0;
 //BA.debugLineNum = 37;BA.debugLine="Dim LightCyan As Int: LightCyan = Colors.RGB(0, 9";
_lightcyan = anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (0),(int) (95),(int) (170));
 //BA.debugLineNum = 38;BA.debugLine="UnderActionBar.Initialize(\"\")";
mostCurrent._underactionbar.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 39;BA.debugLine="UnderActionBar.Color = LightCyan";
mostCurrent._underactionbar.setColor(_lightcyan);
 //BA.debugLineNum = 40;BA.debugLine="Activity.AddView(UnderActionBar, 0, BarSize, 100%";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._underactionbar.getObject()),(int) (0),_barsize,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA)-_barsize));
 //BA.debugLineNum = 42;BA.debugLine="PanelWithSidebar.Initialize(UnderActionBar, 190di";
mostCurrent._panelwithsidebar._initialize(mostCurrent.activityBA,mostCurrent._underactionbar,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (190)),(byte) (2),(byte) (1),(int) (500),(int) (500));
 //BA.debugLineNum = 43;BA.debugLine="PanelWithSidebar.ContentPanel.Color = LightCyan";
mostCurrent._panelwithsidebar._contentpanel().setColor(_lightcyan);
 //BA.debugLineNum = 44;BA.debugLine="PanelWithSidebar.Sidebar.Background = PanelWithSi";
mostCurrent._panelwithsidebar._sidebar().setBackground((android.graphics.drawable.Drawable)(mostCurrent._panelwithsidebar._loaddrawable("popup_inline_error")));
 //BA.debugLineNum = 45;BA.debugLine="PanelWithSidebar.SetOnChangeListeners(Me, \"Menu_o";
mostCurrent._panelwithsidebar._setonchangelisteners(building.getObject(),"Menu_onFullyOpen","Menu_onFullyClosed","Menu_onMove");
 //BA.debugLineNum = 47;BA.debugLine="lvMenu.Initialize(\"lvMenu\")";
mostCurrent._lvmenu.Initialize(mostCurrent.activityBA,"lvMenu");
 //BA.debugLineNum = 48;BA.debugLine="lvMenu.AddSingleLine(\"Building By No House\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Building By No House"));
 //BA.debugLineNum = 49;BA.debugLine="lvMenu.AddSingleLine(\"Building By Owner\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Building By Owner"));
 //BA.debugLineNum = 50;BA.debugLine="lvMenu.AddSingleLine(\"Building Status\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Building Status"));
 //BA.debugLineNum = 51;BA.debugLine="lvMenu.AddSingleLine(\"Building Condition\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Building Condition"));
 //BA.debugLineNum = 52;BA.debugLine="lvMenu.SingleLineLayout.Label.TextColor = Colors.";
mostCurrent._lvmenu.getSingleLineLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 53;BA.debugLine="lvMenu.Color = Colors.Transparent";
mostCurrent._lvmenu.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 54;BA.debugLine="lvMenu.ScrollingBackgroundColor = Colors.Transpar";
mostCurrent._lvmenu.setScrollingBackgroundColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 55;BA.debugLine="PanelWithSidebar.Sidebar.AddView(lvMenu, 20dip, 2";
mostCurrent._panelwithsidebar._sidebar().AddView((android.view.View)(mostCurrent._lvmenu.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),(int) (-1),(int) (-1));
 //BA.debugLineNum = 64;BA.debugLine="Webview1.Initialize(\"\")";
mostCurrent._webview1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 65;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/laye";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php");
 //BA.debugLineNum = 66;BA.debugLine="PanelWithSidebar.ContentPanel.AddView(Webview1, 0";
mostCurrent._panelwithsidebar._contentpanel().AddView((android.view.View)(mostCurrent._webview1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0))),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 //BA.debugLineNum = 68;BA.debugLine="btnMenu.Initialize(\"\")";
mostCurrent._btnmenu.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 69;BA.debugLine="btnMenu.SetBackgroundImage(LoadBitmap(File.DirAss";
mostCurrent._btnmenu.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"menu.png").getObject()));
 //BA.debugLineNum = 70;BA.debugLine="FakeActionBar.AddView(btnMenu, 100%x - BarSize, 0";
mostCurrent._fakeactionbar.AddView((android.view.View)(mostCurrent._btnmenu.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-_barsize),(int) (0),_barsize,_barsize);
 //BA.debugLineNum = 71;BA.debugLine="PanelWithSidebar.SetOpenCloseButton(btnMenu)";
mostCurrent._panelwithsidebar._setopenclosebutton((anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(mostCurrent._btnmenu.getObject())));
 //BA.debugLineNum = 74;BA.debugLine="Activity.LoadLayout(\"building\")";
mostCurrent._activity.LoadLayout("building",mostCurrent.activityBA);
 //BA.debugLineNum = 77;BA.debugLine="Panel1.Width=Activity.Width";
mostCurrent._panel1.setWidth(mostCurrent._activity.getWidth());
 //BA.debugLineNum = 78;BA.debugLine="Panel1.Height=Activity.Height/3*2";
mostCurrent._panel1.setHeight((int) (mostCurrent._activity.getHeight()/(double)3*2));
 //BA.debugLineNum = 80;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 85;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 86;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 82;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 83;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
String _citizen_id = "";
 //BA.debugLineNum = 194;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 195;BA.debugLine="Dim citizen_id As String";
_citizen_id = "";
 //BA.debugLineNum = 196;BA.debugLine="citizen_id=EditText1.Text";
_citizen_id = mostCurrent._edittext1.getText();
 //BA.debugLineNum = 198;BA.debugLine="If citizen_id=Null Then";
if (_citizen_id== null) { 
 //BA.debugLineNum = 199;BA.debugLine="citizen_id=\"\"";
_citizen_id = "";
 };
 //BA.debugLineNum = 202;BA.debugLine="job2.Initialize(\"building\", Me)";
mostCurrent._job2._initialize(processBA,"building",building.getObject());
 //BA.debugLineNum = 203;BA.debugLine="job2.PostString(domain&\"ta_v2/endpoint/building_b";
mostCurrent._job2._poststring(mostCurrent._domain+"ta_v2/endpoint/building_bo.php","citizen_id="+_citizen_id);
 //BA.debugLineNum = 205;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/buil";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/building_bo.php?citizen_id="+_citizen_id);
 //BA.debugLineNum = 207;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim FakeActionBar, UnderActionBar As Panel";
mostCurrent._fakeactionbar = new anywheresoftware.b4a.objects.PanelWrapper();
mostCurrent._underactionbar = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 14;BA.debugLine="Dim PanelWithSidebar As ClsSlidingSidebar";
mostCurrent._panelwithsidebar = new b4a.example.clsslidingsidebar();
 //BA.debugLineNum = 15;BA.debugLine="Dim btnMenu As Button";
mostCurrent._btnmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 16;BA.debugLine="Dim lvMenu As ListView";
mostCurrent._lvmenu = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim lblInfo As Label";
mostCurrent._lblinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim Webview1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim domain As String";
mostCurrent._domain = "";
 //BA.debugLineNum = 20;BA.debugLine="domain=\"http://d3e0d215.ngrok.io/\"";
mostCurrent._domain = "http://d3e0d215.ngrok.io/";
 //BA.debugLineNum = 21;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Private EditText1 As EditText";
mostCurrent._edittext1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Dim job2 As HttpJob";
mostCurrent._job2 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 25;BA.debugLine="Dim length As Int";
_length = 0;
 //BA.debugLineNum = 28;BA.debugLine="Dim SD As SlidingPanels";
mostCurrent._sd = new b4a.example.slidingpanels();
 //BA.debugLineNum = 29;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
anywheresoftware.b4a.objects.collections.List _features = null;
anywheresoftware.b4a.objects.collections.Map _colfeatures = null;
anywheresoftware.b4a.objects.collections.Map _geometry = null;
anywheresoftware.b4a.objects.collections.List _coordinates = null;
anywheresoftware.b4a.objects.collections.List _colcoordinates = null;
anywheresoftware.b4a.objects.collections.List _colcolcoordinates = null;
anywheresoftware.b4a.objects.collections.List _colcolcolcoordinates = null;
double _colcolcolcolcoordinates = 0;
String _type = "";
anywheresoftware.b4a.objects.collections.Map _properties = null;
String _clan_name = "";
String _citizen_id = "";
String _gender = "";
String _phone = "";
String _x = "";
String _name = "";
String _y = "";
String _no_house = "";
String _born_date = "";
anywheresoftware.b4a.objects.drawable.CanvasWrapper _cvs = null;
 //BA.debugLineNum = 108;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 109;BA.debugLine="Log(\"JobName = \" & Job.JobName & \", Success = \" &";
anywheresoftware.b4a.keywords.Common.Log("JobName = "+_job._jobname+", Success = "+BA.ObjectToString(_job._success));
 //BA.debugLineNum = 110;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 111;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._jobname,"building")) {
case 0: {
 //BA.debugLineNum = 114;BA.debugLine="Log(Job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 //BA.debugLineNum = 116;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 117;BA.debugLine="parser.Initialize(Job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 118;BA.debugLine="Dim root As Map = parser.NextObject";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root = _parser.NextObject();
 //BA.debugLineNum = 119;BA.debugLine="Dim features As List = root.Get(\"features\")";
_features = new anywheresoftware.b4a.objects.collections.List();
_features.setObject((java.util.List)(_root.Get((Object)("features"))));
 //BA.debugLineNum = 120;BA.debugLine="Log(length)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_length));
 //BA.debugLineNum = 122;BA.debugLine="If length>0 Then";
if (_length>0) { 
 //BA.debugLineNum = 123;BA.debugLine="Do While length>=0";
while (_length>=0) {
 //BA.debugLineNum = 124;BA.debugLine="Log(length)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_length));
 //BA.debugLineNum = 125;BA.debugLine="SD.Panels(length).Visible=False";
mostCurrent._sd._panels[_length].setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 126;BA.debugLine="length=length-1";
_length = (int) (_length-1);
 }
;
 };
 //BA.debugLineNum = 130;BA.debugLine="length=0";
_length = (int) (0);
 //BA.debugLineNum = 131;BA.debugLine="SD.Initialize(\"SD\",300,Activity,Me,False) 'Ini";
mostCurrent._sd._initialize(mostCurrent.activityBA,"SD",(int) (300),(anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(mostCurrent._activity.getObject())),building.getObject(),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 132;BA.debugLine="SD.ModeLittlePanels(15,50%x,50%x,Activity.Heig";
mostCurrent._sd._modelittlepanels((int) (15),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),(int) (mostCurrent._activity.getHeight()/(double)6*5),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 133;BA.debugLine="For Each colfeatures As Map In features";
_colfeatures = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group21 = _features;
final int groupLen21 = group21.getSize()
;int index21 = 0;
;
for (; index21 < groupLen21;index21++){
_colfeatures.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group21.Get(index21)));
 //BA.debugLineNum = 134;BA.debugLine="Dim geometry As Map = colfeatures.Get(\"geomet";
_geometry = new anywheresoftware.b4a.objects.collections.Map();
_geometry.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_colfeatures.Get((Object)("geometry"))));
 //BA.debugLineNum = 135;BA.debugLine="Dim coordinates As List = geometry.Get(\"coord";
_coordinates = new anywheresoftware.b4a.objects.collections.List();
_coordinates.setObject((java.util.List)(_geometry.Get((Object)("coordinates"))));
 //BA.debugLineNum = 136;BA.debugLine="For Each colcoordinates As List In coordinate";
_colcoordinates = new anywheresoftware.b4a.objects.collections.List();
{
final anywheresoftware.b4a.BA.IterableList group24 = _coordinates;
final int groupLen24 = group24.getSize()
;int index24 = 0;
;
for (; index24 < groupLen24;index24++){
_colcoordinates.setObject((java.util.List)(group24.Get(index24)));
 //BA.debugLineNum = 137;BA.debugLine="For Each colcolcoordinates As List In colcoo";
_colcolcoordinates = new anywheresoftware.b4a.objects.collections.List();
{
final anywheresoftware.b4a.BA.IterableList group25 = _colcoordinates;
final int groupLen25 = group25.getSize()
;int index25 = 0;
;
for (; index25 < groupLen25;index25++){
_colcolcoordinates.setObject((java.util.List)(group25.Get(index25)));
 //BA.debugLineNum = 138;BA.debugLine="For Each colcolcolcoordinates As List In co";
_colcolcolcoordinates = new anywheresoftware.b4a.objects.collections.List();
{
final anywheresoftware.b4a.BA.IterableList group26 = _colcolcoordinates;
final int groupLen26 = group26.getSize()
;int index26 = 0;
;
for (; index26 < groupLen26;index26++){
_colcolcolcoordinates.setObject((java.util.List)(group26.Get(index26)));
 //BA.debugLineNum = 139;BA.debugLine="For Each colcolcolcolcoordinates As Double";
{
final anywheresoftware.b4a.BA.IterableList group27 = _colcolcolcoordinates;
final int groupLen27 = group27.getSize()
;int index27 = 0;
;
for (; index27 < groupLen27;index27++){
_colcolcolcolcoordinates = (double)(BA.ObjectToNumber(group27.Get(index27)));
 }
};
 }
};
 }
};
 //BA.debugLineNum = 143;BA.debugLine="length=length+1";
_length = (int) (_length+1);
 }
};
 //BA.debugLineNum = 145;BA.debugLine="Dim Type As String = geometry.Get(\"type\")";
_type = BA.ObjectToString(_geometry.Get((Object)("type")));
 //BA.debugLineNum = 146;BA.debugLine="Dim Type As String = colfeatures.Get(\"type\")";
_type = BA.ObjectToString(_colfeatures.Get((Object)("type")));
 //BA.debugLineNum = 147;BA.debugLine="Dim properties As Map = colfeatures.Get(\"prop";
_properties = new anywheresoftware.b4a.objects.collections.Map();
_properties.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_colfeatures.Get((Object)("properties"))));
 //BA.debugLineNum = 148;BA.debugLine="Dim clan_name As String = properties.Get(\"cla";
_clan_name = BA.ObjectToString(_properties.Get((Object)("clan_name")));
 //BA.debugLineNum = 149;BA.debugLine="Dim citizen_id As String = properties.Get(\"ci";
_citizen_id = BA.ObjectToString(_properties.Get((Object)("citizen_id")));
 //BA.debugLineNum = 150;BA.debugLine="Dim gender As String = properties.Get(\"gender";
_gender = BA.ObjectToString(_properties.Get((Object)("gender")));
 //BA.debugLineNum = 151;BA.debugLine="Dim phone As String = properties.Get(\"phone\")";
_phone = BA.ObjectToString(_properties.Get((Object)("phone")));
 //BA.debugLineNum = 152;BA.debugLine="Dim x As String = properties.Get(\"x\")";
_x = BA.ObjectToString(_properties.Get((Object)("x")));
 //BA.debugLineNum = 153;BA.debugLine="Dim name As String = properties.Get(\"name\")";
_name = BA.ObjectToString(_properties.Get((Object)("name")));
 //BA.debugLineNum = 154;BA.debugLine="Dim y As String = properties.Get(\"y\")";
_y = BA.ObjectToString(_properties.Get((Object)("y")));
 //BA.debugLineNum = 155;BA.debugLine="Dim no_house As String = properties.Get(\"no_h";
_no_house = BA.ObjectToString(_properties.Get((Object)("no_house")));
 //BA.debugLineNum = 156;BA.debugLine="Dim born_date As String = properties.Get(\"bor";
_born_date = BA.ObjectToString(_properties.Get((Object)("born_date")));
 //BA.debugLineNum = 160;BA.debugLine="SD.panels(length).Color = Colors.RGB(Rnd(0,25";
mostCurrent._sd._panels[_length].setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB(anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (256)),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (256)),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (256))));
 //BA.debugLineNum = 161;BA.debugLine="Dim cvs As Canvas";
_cvs = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 162;BA.debugLine="cvs.Initialize(SD.Panels(length))";
_cvs.Initialize((android.view.View)(mostCurrent._sd._panels[_length].getObject()));
 //BA.debugLineNum = 163;BA.debugLine="cvs.DrawText(\"House: \"&no_house,SD.panels(len";
_cvs.DrawText(mostCurrent.activityBA,"House: "+_no_house,(float) (mostCurrent._sd._panels[_length].getWidth()/(double)3),(float) (mostCurrent._sd._panels[_length].getHeight()/(double)3),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA)/(double)anywheresoftware.b4a.keywords.Common.Density),anywheresoftware.b4a.keywords.Common.Colors.White,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 164;BA.debugLine="cvs.DrawText(x,SD.panels(length).Width/3*2,SD";
_cvs.DrawText(mostCurrent.activityBA,_x,(float) (mostCurrent._sd._panels[_length].getWidth()/(double)3*2),(float) (mostCurrent._sd._panels[_length].getHeight()/(double)3+40),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (3),mostCurrent.activityBA)/(double)anywheresoftware.b4a.keywords.Common.Density),anywheresoftware.b4a.keywords.Common.Colors.White,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 165;BA.debugLine="cvs.DrawText(y,SD.panels(length).Width/3*2,SD";
_cvs.DrawText(mostCurrent.activityBA,_y,(float) (mostCurrent._sd._panels[_length].getWidth()/(double)3*2),(float) (mostCurrent._sd._panels[_length].getHeight()/(double)3*2+10),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (3),mostCurrent.activityBA)/(double)anywheresoftware.b4a.keywords.Common.Density),anywheresoftware.b4a.keywords.Common.Colors.White,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 167;BA.debugLine="Log(no_house&x&y)";
anywheresoftware.b4a.keywords.Common.Log(_no_house+_x+_y);
 }
};
 //BA.debugLineNum = 169;BA.debugLine="Dim Type As String = root.Get(\"type\")";
_type = BA.ObjectToString(_root.Get((Object)("type")));
 //BA.debugLineNum = 181;BA.debugLine="SD.Start(0) 'Start the SlidingPanels.";
mostCurrent._sd._start((int) (0));
 break; }
}
;
 }else {
 //BA.debugLineNum = 187;BA.debugLine="Log(\"Error: \" & Job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.Log("Error: "+_job._errormessage);
 //BA.debugLineNum = 188;BA.debugLine="ToastMessageShow(\"Error: \" & Job.ErrorMessage, T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: "+_job._errormessage),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 190;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 191;BA.debugLine="End Sub";
return "";
}
public static String  _lvmenu_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 88;BA.debugLine="Sub lvMenu_ItemClick (Position As Int, Value As Ob";
 //BA.debugLineNum = 90;BA.debugLine="PanelWithSidebar.CloseSidebar";
mostCurrent._panelwithsidebar._closesidebar();
 //BA.debugLineNum = 91;BA.debugLine="End Sub";
return "";
}
public static String  _menu_onfullyclosed() throws Exception{
 //BA.debugLineNum = 97;BA.debugLine="Sub Menu_onFullyClosed";
 //BA.debugLineNum = 98;BA.debugLine="Log(\"FULLY CLOSED\")";
anywheresoftware.b4a.keywords.Common.Log("FULLY CLOSED");
 //BA.debugLineNum = 99;BA.debugLine="End Sub";
return "";
}
public static String  _menu_onfullyopen() throws Exception{
 //BA.debugLineNum = 93;BA.debugLine="Sub Menu_onFullyOpen";
 //BA.debugLineNum = 94;BA.debugLine="Log(\"FULLY OPEN\")";
anywheresoftware.b4a.keywords.Common.Log("FULLY OPEN");
 //BA.debugLineNum = 95;BA.debugLine="End Sub";
return "";
}
public static String  _menu_onmove(boolean _isopening) throws Exception{
 //BA.debugLineNum = 101;BA.debugLine="Sub Menu_onMove(IsOpening As Boolean)";
 //BA.debugLineNum = 102;BA.debugLine="Log(\"MOVE IsOpening=\" & IsOpening)";
anywheresoftware.b4a.keywords.Common.Log("MOVE IsOpening="+BA.ObjectToString(_isopening));
 //BA.debugLineNum = 103;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
