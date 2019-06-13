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

public class layer extends Activity implements B4AActivity{
	public static layer mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.layer");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (layer).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.layer");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.layer", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (layer) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (layer) Resume **");
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
		return layer.class;
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
        BA.LogInfo("** Activity (layer) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            layer mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (layer) Resume **");
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
public static anywheresoftware.b4a.objects.Timer _tmrload = null;
public b4a.example.slidemenu _sm = null;
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel2 = null;
public static String _domain = "";
public anywheresoftware.b4a.objects.PanelWrapper _panel3 = null;
public thalmy.webviewxtended.xtender _wvxtender = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.menu _menu = null;
public b4a.example.citizen _citizen = null;
public b4a.example.building _building = null;

public static void initializeProcessGlobals() {
             try {
                Class.forName(BA.applicationContext.getPackageName() + ".main").getMethod("initializeProcessGlobals").invoke(null, null);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
}
public static String  _activity_create(boolean _firsttime) throws Exception{
 //BA.debugLineNum = 26;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 29;BA.debugLine="Activity.LoadLayout(\"layer\")";
mostCurrent._activity.LoadLayout("layer",mostCurrent.activityBA);
 //BA.debugLineNum = 30;BA.debugLine="sm.Initialize(Activity, Me, \"SlideMenu\", 42dip, 1";
mostCurrent._sm._initialize(mostCurrent.activityBA,mostCurrent._activity,layer.getObject(),"SlideMenu",anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (42)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (180)));
 //BA.debugLineNum = 32;BA.debugLine="sm.AddItem(\"All Items\", LoadBitmap(File.DirAssets";
mostCurrent._sm._additem("All Items",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"bomb.png"),(Object)(1));
 //BA.debugLineNum = 33;BA.debugLine="sm.AddItem(\"Block A\", LoadBitmap(File.DirAssets,";
mostCurrent._sm._additem("Block A",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"book_add.png"),(Object)(2));
 //BA.debugLineNum = 34;BA.debugLine="sm.AddItem(\"Block B\", LoadBitmap(File.DirAssets,";
mostCurrent._sm._additem("Block B",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"book_add.png"),(Object)(3));
 //BA.debugLineNum = 35;BA.debugLine="sm.AddItem(\"Block C\", LoadBitmap(File.DirAssets,";
mostCurrent._sm._additem("Block C",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"book_add.png"),(Object)(4));
 //BA.debugLineNum = 36;BA.debugLine="sm.AddItem(\"Block D\", LoadBitmap(File.DirAssets,";
mostCurrent._sm._additem("Block D",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"book_add.png"),(Object)(5));
 //BA.debugLineNum = 37;BA.debugLine="sm.AddItem(\"Block E\", LoadBitmap(File.DirAssets,";
mostCurrent._sm._additem("Block E",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"book_add.png"),(Object)(6));
 //BA.debugLineNum = 38;BA.debugLine="sm.AddItem(\"Block F\", LoadBitmap(File.DirAssets,";
mostCurrent._sm._additem("Block F",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"book_add.png"),(Object)(7));
 //BA.debugLineNum = 39;BA.debugLine="sm.AddItem(\"Block G\", LoadBitmap(File.DirAssets,";
mostCurrent._sm._additem("Block G",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"book_add.png"),(Object)(8));
 //BA.debugLineNum = 40;BA.debugLine="sm.AddItem(\"Block H\", LoadBitmap(File.DirAssets,";
mostCurrent._sm._additem("Block H",anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"book_add.png"),(Object)(9));
 //BA.debugLineNum = 41;BA.debugLine="WebView1.Width=Activity.Width";
mostCurrent._webview1.setWidth(mostCurrent._activity.getWidth());
 //BA.debugLineNum = 42;BA.debugLine="WebView1.Height=Activity.Height";
mostCurrent._webview1.setHeight(mostCurrent._activity.getHeight());
 //BA.debugLineNum = 43;BA.debugLine="Panel2.Width=WebView1.Width";
mostCurrent._panel2.setWidth(mostCurrent._webview1.getWidth());
 //BA.debugLineNum = 44;BA.debugLine="Panel2.Height=WebView1.Height";
mostCurrent._panel2.setHeight(mostCurrent._webview1.getHeight());
 //BA.debugLineNum = 45;BA.debugLine="tmrLoad.Initialize(\"tmrLoad\", 200) ' 1000 = 1 sec";
_tmrload.Initialize(processBA,"tmrLoad",(long) (200));
 //BA.debugLineNum = 46;BA.debugLine="WebView1.LoadUrl(domain&\"ta_v2/endpoint/view/laye";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php");
 //BA.debugLineNum = 47;BA.debugLine="tmrLoad.Enabled = True";
_tmrload.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 49;BA.debugLine="Log(domain&\"ta_v2/endpoint/view/layers.php\")";
anywheresoftware.b4a.keywords.Common.Log(mostCurrent._domain+"ta_v2/endpoint/view/layers.php");
 //BA.debugLineNum = 50;BA.debugLine="Panel3.Height=Activity.Height/2";
mostCurrent._panel3.setHeight((int) (mostCurrent._activity.getHeight()/(double)2));
 //BA.debugLineNum = 51;BA.debugLine="Panel3.Width=Activity.Width";
mostCurrent._panel3.setWidth(mostCurrent._activity.getWidth());
 //BA.debugLineNum = 55;BA.debugLine="End Sub";
return "";
}
public static boolean  _activity_keypress(int _keycode) throws Exception{
 //BA.debugLineNum = 80;BA.debugLine="Sub Activity_KeyPress (KeyCode As Int) As Boolean";
 //BA.debugLineNum = 82;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_BACK And sm.isVisib";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_BACK && mostCurrent._sm._isvisible()) { 
 //BA.debugLineNum = 83;BA.debugLine="sm.Hide";
mostCurrent._sm._hide();
 //BA.debugLineNum = 84;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 88;BA.debugLine="If KeyCode = KeyCodes.KEYCODE_MENU Then";
if (_keycode==anywheresoftware.b4a.keywords.Common.KeyCodes.KEYCODE_MENU) { 
 //BA.debugLineNum = 89;BA.debugLine="If sm.isVisible Then sm.Hide Else sm.Show";
if (mostCurrent._sm._isvisible()) { 
mostCurrent._sm._hide();}
else {
mostCurrent._sm._show();};
 //BA.debugLineNum = 90;BA.debugLine="Return True";
if (true) return anywheresoftware.b4a.keywords.Common.True;
 };
 //BA.debugLineNum = 92;BA.debugLine="End Sub";
return false;
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 75;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 77;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 71;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 73;BA.debugLine="End Sub";
return "";
}
public static String  _btnshow_click() throws Exception{
 //BA.debugLineNum = 95;BA.debugLine="Sub btnShow_Click";
 //BA.debugLineNum = 96;BA.debugLine="sm.Show";
mostCurrent._sm._show();
 //BA.debugLineNum = 97;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 15;BA.debugLine="Dim sm As SlideMenu";
mostCurrent._sm = new b4a.example.slidemenu();
 //BA.debugLineNum = 17;BA.debugLine="Private WebView1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Private Panel2 As Panel";
mostCurrent._panel2 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 19;BA.debugLine="Dim domain As String";
mostCurrent._domain = "";
 //BA.debugLineNum = 20;BA.debugLine="domain=\"http://d3e0d215.ngrok.io/\"";
mostCurrent._domain = "http://d3e0d215.ngrok.io/";
 //BA.debugLineNum = 21;BA.debugLine="Private Panel3 As Panel";
mostCurrent._panel3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim wvXtender As WebViewXtender";
mostCurrent._wvxtender = new thalmy.webviewxtended.xtender();
 //BA.debugLineNum = 23;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 24;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 9;BA.debugLine="Dim tmrLoad As Timer";
_tmrload = new anywheresoftware.b4a.objects.Timer();
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _slidemenu_click(Object _item) throws Exception{
 //BA.debugLineNum = 100;BA.debugLine="Sub SlideMenu_Click(Item As Object)";
 //BA.debugLineNum = 101;BA.debugLine="ToastMessageShow(\"Item clicked: \" & Item, False)";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Item clicked: "+BA.ObjectToString(_item)),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 102;BA.debugLine="If Item=1 Then";
if ((_item).equals((Object)(1))) { 
 //BA.debugLineNum = 103;BA.debugLine="tmrLoad.Initialize(\"tmrLoad\", 200) ' 1000 = 1 se";
_tmrload.Initialize(processBA,"tmrLoad",(long) (200));
 //BA.debugLineNum = 104;BA.debugLine="WebView1.LoadUrl(domain&\"ta_v2/endpoint/view/lay";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php?request=all");
 //BA.debugLineNum = 105;BA.debugLine="tmrLoad.Enabled = True";
_tmrload.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if((_item).equals((Object)(2))) { 
 //BA.debugLineNum = 107;BA.debugLine="tmrLoad.Initialize(\"tmrLoad\", 200) ' 1000 = 1 se";
_tmrload.Initialize(processBA,"tmrLoad",(long) (200));
 //BA.debugLineNum = 108;BA.debugLine="WebView1.LoadUrl(domain&\"ta_v2/endpoint/view/";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php?request=A");
 //BA.debugLineNum = 109;BA.debugLine="tmrLoad.Enabled = True";
_tmrload.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if((_item).equals((Object)(3))) { 
 //BA.debugLineNum = 111;BA.debugLine="tmrLoad.Initialize(\"tmrLoad\", 200) ' 1000 = 1 se";
_tmrload.Initialize(processBA,"tmrLoad",(long) (200));
 //BA.debugLineNum = 112;BA.debugLine="WebView1.LoadUrl(domain&\"ta_v2/endpoint/view/lay";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php?request=B");
 //BA.debugLineNum = 113;BA.debugLine="tmrLoad.Enabled = True";
_tmrload.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if((_item).equals((Object)(4))) { 
 //BA.debugLineNum = 115;BA.debugLine="tmrLoad.Initialize(\"tmrLoad\", 200) ' 1000 = 1 se";
_tmrload.Initialize(processBA,"tmrLoad",(long) (200));
 //BA.debugLineNum = 116;BA.debugLine="WebView1.LoadUrl(domain&\"ta_v2/endpoint/view/lay";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php?request=C");
 //BA.debugLineNum = 117;BA.debugLine="tmrLoad.Enabled = True";
_tmrload.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if((_item).equals((Object)(5))) { 
 //BA.debugLineNum = 119;BA.debugLine="tmrLoad.Initialize(\"tmrLoad\", 200) ' 1000 = 1 se";
_tmrload.Initialize(processBA,"tmrLoad",(long) (200));
 //BA.debugLineNum = 120;BA.debugLine="WebView1.LoadUrl(domain&\"ta_v2/endpoint/view/lay";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php?request=D");
 //BA.debugLineNum = 121;BA.debugLine="tmrLoad.Enabled = True";
_tmrload.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if((_item).equals((Object)(6))) { 
 //BA.debugLineNum = 123;BA.debugLine="tmrLoad.Initialize(\"tmrLoad\", 200) ' 1000 = 1 se";
_tmrload.Initialize(processBA,"tmrLoad",(long) (200));
 //BA.debugLineNum = 124;BA.debugLine="WebView1.LoadUrl(domain&\"ta_v2/endpoint/view/lay";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php?request=E");
 //BA.debugLineNum = 125;BA.debugLine="tmrLoad.Enabled = True";
_tmrload.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if((_item).equals((Object)(7))) { 
 //BA.debugLineNum = 127;BA.debugLine="tmrLoad.Initialize(\"tmrLoad\", 200) ' 1000 = 1 se";
_tmrload.Initialize(processBA,"tmrLoad",(long) (200));
 //BA.debugLineNum = 128;BA.debugLine="WebView1.LoadUrl(domain&\"ta_v2/endpoint/view/lay";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php?request=F");
 //BA.debugLineNum = 129;BA.debugLine="tmrLoad.Enabled = True";
_tmrload.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if((_item).equals((Object)(8))) { 
 //BA.debugLineNum = 131;BA.debugLine="tmrLoad.Initialize(\"tmrLoad\", 200) ' 1000 = 1 se";
_tmrload.Initialize(processBA,"tmrLoad",(long) (200));
 //BA.debugLineNum = 132;BA.debugLine="WebView1.LoadUrl(domain&\"ta_v2/endpoint/view/lay";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php?request=G");
 //BA.debugLineNum = 133;BA.debugLine="tmrLoad.Enabled = True";
_tmrload.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 }else if((_item).equals((Object)(9))) { 
 //BA.debugLineNum = 135;BA.debugLine="tmrLoad.Initialize(\"tmrLoad\", 200) ' 1000 = 1 se";
_tmrload.Initialize(processBA,"tmrLoad",(long) (200));
 //BA.debugLineNum = 136;BA.debugLine="WebView1.LoadUrl(domain&\"ta_v2/endpoint/view/lay";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php?request=H");
 //BA.debugLineNum = 137;BA.debugLine="tmrLoad.Enabled = True";
_tmrload.setEnabled(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 139;BA.debugLine="End Sub";
return "";
}
public static String  _tmrload_tick() throws Exception{
 //BA.debugLineNum = 58;BA.debugLine="Sub tmrLoad_Tick";
 //BA.debugLineNum = 60;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 //BA.debugLineNum = 61;BA.debugLine="Label1.Text = \"Loading \"&wvXtender.getProgress(We";
mostCurrent._label1.setText(BA.ObjectToCharSequence("Loading "+BA.NumberToString(mostCurrent._wvxtender.getProgress((android.webkit.WebView)(mostCurrent._webview1.getObject())))+"%"));
 //BA.debugLineNum = 62;BA.debugLine="DoEvents";
anywheresoftware.b4a.keywords.Common.DoEvents();
 //BA.debugLineNum = 63;BA.debugLine="If Label1.Text = \"Loading 100%\" Then";
if ((mostCurrent._label1.getText()).equals("Loading 100%")) { 
 //BA.debugLineNum = 64;BA.debugLine="tmrLoad.Enabled = False";
_tmrload.setEnabled(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 65;BA.debugLine="Label1.Text=\"\"";
mostCurrent._label1.setText(BA.ObjectToCharSequence(""));
 };
 //BA.debugLineNum = 69;BA.debugLine="End Sub";
return "";
}
}
