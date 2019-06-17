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

public class citizen extends Activity implements B4AActivity{
	public static citizen mostCurrent;
	static boolean afterFirstLayout;
	static boolean isFirst = true;
    private static boolean processGlobalsRun = false;
	BALayout layout;
	public static BA processBA;
	BA activityBA;
    ActivityWrapper _activity;
    java.util.ArrayList<B4AMenuItem> menuItems;
	public static final boolean fullScreen = false;
	public static final boolean includeTitle = false;
    public static WeakReference<Activity> previousOne;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mostCurrent = this;
		if (processBA == null) {
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.citizen");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (citizen).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.citizen");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.citizen", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (citizen) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (citizen) Resume **");
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
		return citizen.class;
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
        BA.LogInfo("** Activity (citizen) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            citizen mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (citizen) Resume **");
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
public static int _i = 0;
public anywheresoftware.b4a.samples.httputils2.httpjob _job2 = null;
public anywheresoftware.b4a.objects.collections.JSONParser _json = null;
public anywheresoftware.b4a.objects.collections.Map _map1 = null;
public static String[] _arr = null;
public anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext1 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel6 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel4 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel5 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel8 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel7 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel9 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext2 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.menu _menu = null;
public b4a.example.layer _layer = null;
public b4a.example.building _building = null;
public b4a.example.land _land = null;

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
 //BA.debugLineNum = 52;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 53;BA.debugLine="Activity.LoadLayout(\"citizen\")";
mostCurrent._activity.LoadLayout("citizen",mostCurrent.activityBA);
 //BA.debugLineNum = 54;BA.debugLine="Dim BarSize As Int: BarSize = 60dip";
_barsize = 0;
 //BA.debugLineNum = 54;BA.debugLine="Dim BarSize As Int: BarSize = 60dip";
_barsize = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60));
 //BA.debugLineNum = 55;BA.debugLine="FakeActionBar.Initialize(\"\")";
mostCurrent._fakeactionbar.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 56;BA.debugLine="FakeActionBar.Color = Colors.RGB(20, 20, 100) 'Da";
mostCurrent._fakeactionbar.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (20),(int) (20),(int) (100)));
 //BA.debugLineNum = 57;BA.debugLine="Activity.AddView(FakeActionBar, 0, 0, 100%x, BarS";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fakeactionbar.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),_barsize);
 //BA.debugLineNum = 59;BA.debugLine="Dim LightCyan As Int: LightCyan = Colors.RGB(0, 9";
_lightcyan = 0;
 //BA.debugLineNum = 59;BA.debugLine="Dim LightCyan As Int: LightCyan = Colors.RGB(0, 9";
_lightcyan = anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (0),(int) (95),(int) (170));
 //BA.debugLineNum = 60;BA.debugLine="UnderActionBar.Initialize(\"\")";
mostCurrent._underactionbar.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 61;BA.debugLine="UnderActionBar.Color = LightCyan";
mostCurrent._underactionbar.setColor(_lightcyan);
 //BA.debugLineNum = 62;BA.debugLine="Activity.AddView(UnderActionBar, 0, BarSize, 100%";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._underactionbar.getObject()),(int) (0),_barsize,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-_barsize));
 //BA.debugLineNum = 64;BA.debugLine="PanelWithSidebar.Initialize(UnderActionBar, 190di";
mostCurrent._panelwithsidebar._initialize(mostCurrent.activityBA,mostCurrent._underactionbar,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (190)),(byte) (2),(byte) (1),(int) (500),(int) (500));
 //BA.debugLineNum = 65;BA.debugLine="PanelWithSidebar.ContentPanel.Color = LightCyan";
mostCurrent._panelwithsidebar._contentpanel().setColor(_lightcyan);
 //BA.debugLineNum = 66;BA.debugLine="PanelWithSidebar.Sidebar.Background = PanelWithSi";
mostCurrent._panelwithsidebar._sidebar().setBackground((android.graphics.drawable.Drawable)(mostCurrent._panelwithsidebar._loaddrawable("popup_inline_error")));
 //BA.debugLineNum = 67;BA.debugLine="PanelWithSidebar.SetOnChangeListeners(Me, \"Menu_o";
mostCurrent._panelwithsidebar._setonchangelisteners(citizen.getObject(),"Menu_onFullyOpen","Menu_onFullyClosed","Menu_onMove");
 //BA.debugLineNum = 69;BA.debugLine="lvMenu.Initialize(\"lvMenu\")";
mostCurrent._lvmenu.Initialize(mostCurrent.activityBA,"lvMenu");
 //BA.debugLineNum = 70;BA.debugLine="lvMenu.AddSingleLine(\"Search Citizen\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Search Citizen"));
 //BA.debugLineNum = 71;BA.debugLine="lvMenu.AddSingleLine(\"Family Card\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Family Card"));
 //BA.debugLineNum = 72;BA.debugLine="lvMenu.AddSingleLine(\"Birth Data\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Birth Data"));
 //BA.debugLineNum = 73;BA.debugLine="lvMenu.AddSingleLine(\"Mortality Data\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Mortality Data"));
 //BA.debugLineNum = 74;BA.debugLine="lvMenu.AddSingleLine(\"Outcomes\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Outcomes"));
 //BA.debugLineNum = 75;BA.debugLine="lvMenu.SingleLineLayout.Label.TextColor = Colors.";
mostCurrent._lvmenu.getSingleLineLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 76;BA.debugLine="lvMenu.Color = Colors.Transparent";
mostCurrent._lvmenu.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 77;BA.debugLine="lvMenu.ScrollingBackgroundColor = Colors.Transpar";
mostCurrent._lvmenu.setScrollingBackgroundColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 78;BA.debugLine="PanelWithSidebar.Sidebar.AddView(lvMenu, 20dip, 2";
mostCurrent._panelwithsidebar._sidebar().AddView((android.view.View)(mostCurrent._lvmenu.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),(int) (-1),(int) (-1));
 //BA.debugLineNum = 80;BA.debugLine="Webview1.Initialize(\"\")";
mostCurrent._webview1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 81;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/laye";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php");
 //BA.debugLineNum = 85;BA.debugLine="PanelWithSidebar.ContentPanel.AddView(Webview1, 0";
mostCurrent._panelwithsidebar._contentpanel().AddView((android.view.View)(mostCurrent._webview1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0))),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 //BA.debugLineNum = 87;BA.debugLine="btnMenu.Initialize(\"\")";
mostCurrent._btnmenu.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 88;BA.debugLine="btnMenu.SetBackgroundImage(LoadBitmap(File.DirAss";
mostCurrent._btnmenu.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"menu.png").getObject()));
 //BA.debugLineNum = 89;BA.debugLine="FakeActionBar.AddView(btnMenu, 100%x - BarSize, 0";
mostCurrent._fakeactionbar.AddView((android.view.View)(mostCurrent._btnmenu.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-_barsize),(int) (0),_barsize,_barsize);
 //BA.debugLineNum = 90;BA.debugLine="PanelWithSidebar.SetOpenCloseButton(btnMenu)";
mostCurrent._panelwithsidebar._setopenclosebutton((anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(mostCurrent._btnmenu.getObject())));
 //BA.debugLineNum = 91;BA.debugLine="Activity.LoadLayout(\"citizen\")";
mostCurrent._activity.LoadLayout("citizen",mostCurrent.activityBA);
 //BA.debugLineNum = 93;BA.debugLine="Panel1.Width=Activity.Width";
mostCurrent._panel1.setWidth(mostCurrent._activity.getWidth());
 //BA.debugLineNum = 96;BA.debugLine="EditText1.Visible=False";
mostCurrent._edittext1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 97;BA.debugLine="Button1.Visible=False";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 98;BA.debugLine="Label1.Visible=False";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 99;BA.debugLine="ListView1.Visible=False";
mostCurrent._listview1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 103;BA.debugLine="Panel8.Visible=False";
mostCurrent._panel8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 104;BA.debugLine="Panel3.Visible=False";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 112;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 117;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 118;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 114;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 115;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
String _citizen_id = "";
 //BA.debugLineNum = 180;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 182;BA.debugLine="Dim citizen_id As String";
_citizen_id = "";
 //BA.debugLineNum = 183;BA.debugLine="citizen_id=EditText1.Text";
_citizen_id = mostCurrent._edittext1.getText();
 //BA.debugLineNum = 185;BA.debugLine="If citizen_id=Null Then";
if (_citizen_id== null) { 
 //BA.debugLineNum = 186;BA.debugLine="citizen_id=\"\"";
_citizen_id = "";
 };
 //BA.debugLineNum = 190;BA.debugLine="job2.Initialize(\"Job2\", Me)";
mostCurrent._job2._initialize(processBA,"Job2",citizen.getObject());
 //BA.debugLineNum = 191;BA.debugLine="job2.PostString(domain&\"ta_v2/endpoint/citizen_by";
mostCurrent._job2._poststring(mostCurrent._domain+"ta_v2/endpoint/citizen_by_nik.php","citizen_id="+_citizen_id);
 //BA.debugLineNum = 192;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/land";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/land_owning.php?owner_id="+_citizen_id);
 //BA.debugLineNum = 194;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
String _family_no = "";
 //BA.debugLineNum = 332;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 335;BA.debugLine="Dim family_no As String";
_family_no = "";
 //BA.debugLineNum = 336;BA.debugLine="family_no=EditText2.Text";
_family_no = mostCurrent._edittext2.getText();
 //BA.debugLineNum = 338;BA.debugLine="If family_no=Null Then";
if (_family_no== null) { 
 //BA.debugLineNum = 339;BA.debugLine="family_no=\"\"";
_family_no = "";
 };
 //BA.debugLineNum = 343;BA.debugLine="job2.Initialize(\"family_card\", Me)";
mostCurrent._job2._initialize(processBA,"family_card",citizen.getObject());
 //BA.debugLineNum = 344;BA.debugLine="job2.PostString(domain&\"ta_v2/endpoint/family_car";
mostCurrent._job2._poststring(mostCurrent._domain+"ta_v2/endpoint/family_card.php","family_no="+_family_no);
 //BA.debugLineNum = 345;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/laye";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php");
 //BA.debugLineNum = 348;BA.debugLine="End Sub";
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
 //BA.debugLineNum = 20;BA.debugLine="domain=\"http://7a880193.ngrok.io/\"";
mostCurrent._domain = "http://7a880193.ngrok.io/";
 //BA.debugLineNum = 21;BA.debugLine="Dim i As Int";
_i = 0;
 //BA.debugLineNum = 23;BA.debugLine="Dim job2 As HttpJob";
mostCurrent._job2 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 24;BA.debugLine="Dim JSON As JSONParser";
mostCurrent._json = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 25;BA.debugLine="Dim Map1 As Map";
mostCurrent._map1 = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 26;BA.debugLine="Dim arr() As String";
mostCurrent._arr = new String[(int) (0)];
java.util.Arrays.fill(mostCurrent._arr,"");
 //BA.debugLineNum = 27;BA.debugLine="Dim parser As JSONParser";
mostCurrent._parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 29;BA.debugLine="Private EditText1 As EditText";
mostCurrent._edittext1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 30;BA.debugLine="Private ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Private ListView2 As ListView";
mostCurrent._listview2 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 32;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private Panel3 As Panel";
mostCurrent._panel3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private Panel6 As Panel";
mostCurrent._panel6 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private Panel4 As Panel";
mostCurrent._panel4 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private Panel5 As Panel";
mostCurrent._panel5 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private Panel8 As Panel";
mostCurrent._panel8 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private Panel7 As Panel";
mostCurrent._panel7 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private Panel9 As Panel";
mostCurrent._panel9 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private EditText2 As EditText";
mostCurrent._edittext2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 50;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
int _lv_size1 = 0;
anywheresoftware.b4a.objects.collections.Map _root = null;
String _type = "";
anywheresoftware.b4a.objects.collections.List _results = null;
int _h = 0;
anywheresoftware.b4a.objects.drawable.GradientDrawable _gd1 = null;
anywheresoftware.b4a.objects.drawable.GradientDrawable _gd2 = null;
int _lv_size = 0;
anywheresoftware.b4a.objects.collections.Map _colresults = null;
String _clan_name = "";
String _citizen_name = "";
String _gender = "";
String _nik = "";
String _phone = "";
String _born_date = "";
String _status = "";
 //BA.debugLineNum = 197;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 198;BA.debugLine="Log(\"JobName = \" & Job.JobName & \", Success = \" &";
anywheresoftware.b4a.keywords.Common.Log("JobName = "+_job._jobname+", Success = "+BA.ObjectToString(_job._success));
 //BA.debugLineNum = 199;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 201;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._jobname,"Job2","family_card")) {
case 0: {
 //BA.debugLineNum = 204;BA.debugLine="Log(Job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 //BA.debugLineNum = 205;BA.debugLine="If Job.GetString=\"[]\" Then";
if ((_job._getstring()).equals("[]")) { 
 }else {
 //BA.debugLineNum = 208;BA.debugLine="Dim lv_size1 As Int";
_lv_size1 = 0;
 //BA.debugLineNum = 209;BA.debugLine="lv_size1=ListView1.Size";
_lv_size1 = mostCurrent._listview1.getSize();
 //BA.debugLineNum = 210;BA.debugLine="lv_size1=lv_size1-1";
_lv_size1 = (int) (_lv_size1-1);
 //BA.debugLineNum = 211;BA.debugLine="Log(lv_size1)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_lv_size1));
 //BA.debugLineNum = 212;BA.debugLine="If lv_size1>0 Then";
if (_lv_size1>0) { 
 //BA.debugLineNum = 214;BA.debugLine="Do While lv_size1>=0";
while (_lv_size1>=0) {
 //BA.debugLineNum = 216;BA.debugLine="Log(lv_size1)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_lv_size1));
 //BA.debugLineNum = 217;BA.debugLine="ListView1.RemoveAt(lv_size1)";
mostCurrent._listview1.RemoveAt(_lv_size1);
 //BA.debugLineNum = 218;BA.debugLine="lv_size1=lv_size1-1";
_lv_size1 = (int) (_lv_size1-1);
 }
;
 };
 //BA.debugLineNum = 226;BA.debugLine="JSON.Initialize(Job.GetString)";
mostCurrent._json.Initialize(_job._getstring());
 //BA.debugLineNum = 228;BA.debugLine="Map1 = JSON.NextObject";
mostCurrent._map1 = mostCurrent._json.NextObject();
 //BA.debugLineNum = 229;BA.debugLine="Log(Map1)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._map1));
 //BA.debugLineNum = 239;BA.debugLine="ListView1.SingleLineLayout.Label.TextColor =";
mostCurrent._listview1.getSingleLineLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 241;BA.debugLine="ListView1.AddSingleLine( \"Nik:\"&Map1.Get(\"nik";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Nik:"+BA.ObjectToString(mostCurrent._map1.Get((Object)("nik")))));
 //BA.debugLineNum = 242;BA.debugLine="ListView1.AddSingleLine( \"Status:\"&Map1.Get(\"";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Status:"+BA.ObjectToString(mostCurrent._map1.Get((Object)("status_name")))));
 //BA.debugLineNum = 243;BA.debugLine="ListView1.AddSingleLine( \"Clan:\"&Map1.Get(\"cl";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Clan:"+BA.ObjectToString(mostCurrent._map1.Get((Object)("clan_name")))));
 //BA.debugLineNum = 244;BA.debugLine="ListView1.AddSingleLine( \"Name:\"&Map1.Get(\"ci";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Name:"+BA.ObjectToString(mostCurrent._map1.Get((Object)("citizen_name")))));
 //BA.debugLineNum = 245;BA.debugLine="ListView1.AddSingleLine( \"Phone:\"&Map1.Get(\"p";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Phone:"+BA.ObjectToString(mostCurrent._map1.Get((Object)("phone")))));
 //BA.debugLineNum = 246;BA.debugLine="ListView1.AddSingleLine( \"Gender:\"&Map1.Get(\"";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Gender:"+BA.ObjectToString(mostCurrent._map1.Get((Object)("gender")))));
 //BA.debugLineNum = 247;BA.debugLine="ListView1.SingleLineLayout.Label.TextSize = 1";
mostCurrent._listview1.getSingleLineLayout().Label.setTextSize((float) (14));
 };
 break; }
case 1: {
 //BA.debugLineNum = 252;BA.debugLine="If Job.GetString=\"[]\" Then";
if ((_job._getstring()).equals("[]")) { 
 }else {
 //BA.debugLineNum = 256;BA.debugLine="JSON.Initialize(Job.GetString)";
mostCurrent._json.Initialize(_job._getstring());
 //BA.debugLineNum = 257;BA.debugLine="Map1 = JSON.NextObject";
mostCurrent._map1 = mostCurrent._json.NextObject();
 //BA.debugLineNum = 258;BA.debugLine="Log(Map1)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(mostCurrent._map1));
 //BA.debugLineNum = 261;BA.debugLine="parser.Initialize(Job.GetString)";
mostCurrent._parser.Initialize(_job._getstring());
 //BA.debugLineNum = 262;BA.debugLine="Dim root As Map = parser.NextObject";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root = mostCurrent._parser.NextObject();
 //BA.debugLineNum = 263;BA.debugLine="Dim Type As String = root.Get(\"type\")";
_type = BA.ObjectToString(_root.Get((Object)("type")));
 //BA.debugLineNum = 264;BA.debugLine="Dim results As List = root.Get(\"results\")";
_results = new anywheresoftware.b4a.objects.collections.List();
_results.setObject((java.util.List)(_root.Get((Object)("results"))));
 //BA.debugLineNum = 266;BA.debugLine="Dim H As Int";
_h = 0;
 //BA.debugLineNum = 267;BA.debugLine="H=0";
_h = (int) (0);
 //BA.debugLineNum = 268;BA.debugLine="Dim gd1 As GradientDrawable";
_gd1 = new anywheresoftware.b4a.objects.drawable.GradientDrawable();
 //BA.debugLineNum = 269;BA.debugLine="gd1.Initialize(\"BOTTOM_TOP\", Array As Int (Co";
_gd1.Initialize(BA.getEnumFromString(android.graphics.drawable.GradientDrawable.Orientation.class,"BOTTOM_TOP"),new int[]{anywheresoftware.b4a.keywords.Common.Colors.Magenta,anywheresoftware.b4a.keywords.Common.Colors.Magenta,anywheresoftware.b4a.keywords.Common.Colors.Magenta});
 //BA.debugLineNum = 271;BA.debugLine="Dim gd2 As GradientDrawable";
_gd2 = new anywheresoftware.b4a.objects.drawable.GradientDrawable();
 //BA.debugLineNum = 272;BA.debugLine="gd2.Initialize(\"BOTTOM_TOP\", Array As Int (Co";
_gd2.Initialize(BA.getEnumFromString(android.graphics.drawable.GradientDrawable.Orientation.class,"BOTTOM_TOP"),new int[]{anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.Colors.Gray,anywheresoftware.b4a.keywords.Common.Colors.Gray});
 //BA.debugLineNum = 274;BA.debugLine="Dim lv_size As Int";
_lv_size = 0;
 //BA.debugLineNum = 275;BA.debugLine="lv_size=ListView2.Size";
_lv_size = mostCurrent._listview2.getSize();
 //BA.debugLineNum = 276;BA.debugLine="lv_size=lv_size-1";
_lv_size = (int) (_lv_size-1);
 //BA.debugLineNum = 277;BA.debugLine="Log(lv_size)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_lv_size));
 //BA.debugLineNum = 278;BA.debugLine="If lv_size>0 Then";
if (_lv_size>0) { 
 //BA.debugLineNum = 280;BA.debugLine="Do While lv_size>=0";
while (_lv_size>=0) {
 //BA.debugLineNum = 282;BA.debugLine="Log(lv_size)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_lv_size));
 //BA.debugLineNum = 283;BA.debugLine="ListView2.RemoveAt(lv_size)";
mostCurrent._listview2.RemoveAt(_lv_size);
 //BA.debugLineNum = 284;BA.debugLine="lv_size=lv_size-1";
_lv_size = (int) (_lv_size-1);
 }
;
 };
 //BA.debugLineNum = 295;BA.debugLine="For Each colresults As Map In results";
_colresults = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group58 = _results;
final int groupLen58 = group58.getSize()
;int index58 = 0;
;
for (; index58 < groupLen58;index58++){
_colresults.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group58.Get(index58)));
 //BA.debugLineNum = 296;BA.debugLine="H=H+1";
_h = (int) (_h+1);
 //BA.debugLineNum = 297;BA.debugLine="Dim clan_name As String = colresults.Get(\"cl";
_clan_name = BA.ObjectToString(_colresults.Get((Object)("clan_name")));
 //BA.debugLineNum = 298;BA.debugLine="Dim citizen_name As String = colresults.Get(";
_citizen_name = BA.ObjectToString(_colresults.Get((Object)("citizen_name")));
 //BA.debugLineNum = 301;BA.debugLine="Dim gender As String = colresults.Get(\"gende";
_gender = BA.ObjectToString(_colresults.Get((Object)("gender")));
 //BA.debugLineNum = 305;BA.debugLine="Dim nik As String = colresults.Get(\"nik\")";
_nik = BA.ObjectToString(_colresults.Get((Object)("nik")));
 //BA.debugLineNum = 308;BA.debugLine="Dim phone As String = colresults.Get(\"phone\"";
_phone = BA.ObjectToString(_colresults.Get((Object)("phone")));
 //BA.debugLineNum = 309;BA.debugLine="Dim born_date As String = colresults.Get(\"bo";
_born_date = BA.ObjectToString(_colresults.Get((Object)("born_date")));
 //BA.debugLineNum = 310;BA.debugLine="Dim status As String = colresults.Get(\"statu";
_status = BA.ObjectToString(_colresults.Get((Object)("status")));
 //BA.debugLineNum = 311;BA.debugLine="ListView2.SingleLineLayout.Label.TextColor =";
mostCurrent._listview2.getSingleLineLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 312;BA.debugLine="If H Mod 2=0 Then";
if (_h%2==0) { 
 //BA.debugLineNum = 313;BA.debugLine="ListView2.SingleLineLayout.Background = gd1";
mostCurrent._listview2.getSingleLineLayout().Background = (android.graphics.drawable.Drawable)(_gd1.getObject());
 }else {
 //BA.debugLineNum = 315;BA.debugLine="ListView2.SingleLineLayout.Background = gd2";
mostCurrent._listview2.getSingleLineLayout().Background = (android.graphics.drawable.Drawable)(_gd2.getObject());
 };
 //BA.debugLineNum = 318;BA.debugLine="ListView2.AddSingleLine(\"Clan: \"&clan_name&\"";
mostCurrent._listview2.AddSingleLine(BA.ObjectToCharSequence("Clan: "+_clan_name+" "+" Name: "+_citizen_name+" Gender: "+_gender+" "+" Id: "+_nik+" "+" Status: "+_status+" "+" Born Date: "+_born_date+" "+" Phone: "+_phone+" "));
 //BA.debugLineNum = 319;BA.debugLine="ListView2.SingleLineLayout.Label.TextSize =";
mostCurrent._listview2.getSingleLineLayout().Label.setTextSize((float) (12));
 }
};
 };
 break; }
}
;
 }else {
 //BA.debugLineNum = 326;BA.debugLine="Log(\"Error: \" & Job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.Log("Error: "+_job._errormessage);
 //BA.debugLineNum = 327;BA.debugLine="ToastMessageShow(\"Error: \" & Job.ErrorMessage, T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: "+_job._errormessage),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 329;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 330;BA.debugLine="End Sub";
return "";
}
public static String  _lvmenu_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 120;BA.debugLine="Sub lvMenu_ItemClick (Position As Int, Value As Ob";
 //BA.debugLineNum = 122;BA.debugLine="Log(Position)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_position));
 //BA.debugLineNum = 123;BA.debugLine="Log(Value)";
anywheresoftware.b4a.keywords.Common.Log(BA.ObjectToString(_value));
 //BA.debugLineNum = 124;BA.debugLine="If Position=0 Then";
if (_position==0) { 
 //BA.debugLineNum = 126;BA.debugLine="Panel8.Visible=False";
mostCurrent._panel8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 127;BA.debugLine="Panel3.Visible=False";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 130;BA.debugLine="Panel1.Visible=True";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 131;BA.debugLine="EditText1.Visible=True";
mostCurrent._edittext1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 132;BA.debugLine="Button1.Visible=True";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 133;BA.debugLine="Label1.Visible=True";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 134;BA.debugLine="ListView1.Visible=True";
mostCurrent._listview1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_position==1) { 
 //BA.debugLineNum = 140;BA.debugLine="EditText1.Visible=False";
mostCurrent._edittext1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 141;BA.debugLine="Button1.Visible=False";
mostCurrent._button1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 142;BA.debugLine="Label1.Visible=False";
mostCurrent._label1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 143;BA.debugLine="ListView1.Visible=False";
mostCurrent._listview1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 144;BA.debugLine="Panel3.Visible=False";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 147;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 148;BA.debugLine="Panel8.Left=Panel1.Left";
mostCurrent._panel8.setLeft(mostCurrent._panel1.getLeft());
 //BA.debugLineNum = 150;BA.debugLine="Panel8.Visible=True";
mostCurrent._panel8.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_position==2) { 
 //BA.debugLineNum = 154;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 155;BA.debugLine="Panel8.Visible=False";
mostCurrent._panel8.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 158;BA.debugLine="Panel3.Visible=True";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 159;BA.debugLine="Panel3.Left=Panel1.Left";
mostCurrent._panel3.setLeft(mostCurrent._panel1.getLeft());
 //BA.debugLineNum = 160;BA.debugLine="Panel3.Top=Panel1.Top";
mostCurrent._panel3.setTop(mostCurrent._panel1.getTop());
 };
 //BA.debugLineNum = 164;BA.debugLine="PanelWithSidebar.CloseSidebar";
mostCurrent._panelwithsidebar._closesidebar();
 //BA.debugLineNum = 165;BA.debugLine="End Sub";
return "";
}
public static String  _menu_onfullyclosed() throws Exception{
 //BA.debugLineNum = 171;BA.debugLine="Sub Menu_onFullyClosed";
 //BA.debugLineNum = 173;BA.debugLine="End Sub";
return "";
}
public static String  _menu_onfullyopen() throws Exception{
 //BA.debugLineNum = 167;BA.debugLine="Sub Menu_onFullyOpen";
 //BA.debugLineNum = 169;BA.debugLine="End Sub";
return "";
}
public static String  _menu_onmove(boolean _isopening) throws Exception{
 //BA.debugLineNum = 175;BA.debugLine="Sub Menu_onMove(IsOpening As Boolean)";
 //BA.debugLineNum = 177;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
