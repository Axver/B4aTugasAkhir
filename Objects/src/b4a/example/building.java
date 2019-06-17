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
	public static final boolean includeTitle = false;
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
public static String _id1 = "";
public static String _id2 = "";
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
public anywheresoftware.b4a.objects.PanelWrapper _panel3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel5 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner1 = null;
public anywheresoftware.b4a.objects.SpinnerWrapper _spinner2 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button3 = null;
public anywheresoftware.b4a.objects.collections.Map _spinner1map = null;
public anywheresoftware.b4a.objects.collections.Map _spinner2map = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext2 = null;
public anywheresoftware.b4a.objects.ListViewWrapper _listview1 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.menu _menu = null;
public b4a.example.layer _layer = null;
public b4a.example.citizen _citizen = null;
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
 //BA.debugLineNum = 46;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 48;BA.debugLine="Dim BarSize As Int: BarSize = 60dip";
_barsize = 0;
 //BA.debugLineNum = 48;BA.debugLine="Dim BarSize As Int: BarSize = 60dip";
_barsize = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60));
 //BA.debugLineNum = 49;BA.debugLine="FakeActionBar.Initialize(\"\")";
mostCurrent._fakeactionbar.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 50;BA.debugLine="FakeActionBar.Color = Colors.RGB(20, 20, 100) 'Da";
mostCurrent._fakeactionbar.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (20),(int) (20),(int) (100)));
 //BA.debugLineNum = 51;BA.debugLine="Activity.AddView(FakeActionBar, 0, 0, 100%x, BarS";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fakeactionbar.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),_barsize);
 //BA.debugLineNum = 53;BA.debugLine="Dim LightCyan As Int: LightCyan = Colors.RGB(0, 9";
_lightcyan = 0;
 //BA.debugLineNum = 53;BA.debugLine="Dim LightCyan As Int: LightCyan = Colors.RGB(0, 9";
_lightcyan = anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (0),(int) (95),(int) (170));
 //BA.debugLineNum = 54;BA.debugLine="UnderActionBar.Initialize(\"\")";
mostCurrent._underactionbar.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 55;BA.debugLine="UnderActionBar.Color = LightCyan";
mostCurrent._underactionbar.setColor(_lightcyan);
 //BA.debugLineNum = 56;BA.debugLine="Activity.AddView(UnderActionBar, 0, BarSize, 100%";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._underactionbar.getObject()),(int) (0),_barsize,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA)-_barsize));
 //BA.debugLineNum = 58;BA.debugLine="PanelWithSidebar.Initialize(UnderActionBar, 190di";
mostCurrent._panelwithsidebar._initialize(mostCurrent.activityBA,mostCurrent._underactionbar,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (190)),(byte) (2),(byte) (1),(int) (500),(int) (500));
 //BA.debugLineNum = 59;BA.debugLine="PanelWithSidebar.ContentPanel.Color = LightCyan";
mostCurrent._panelwithsidebar._contentpanel().setColor(_lightcyan);
 //BA.debugLineNum = 60;BA.debugLine="PanelWithSidebar.Sidebar.Background = PanelWithSi";
mostCurrent._panelwithsidebar._sidebar().setBackground((android.graphics.drawable.Drawable)(mostCurrent._panelwithsidebar._loaddrawable("popup_inline_error")));
 //BA.debugLineNum = 61;BA.debugLine="PanelWithSidebar.SetOnChangeListeners(Me, \"Menu_o";
mostCurrent._panelwithsidebar._setonchangelisteners(building.getObject(),"Menu_onFullyOpen","Menu_onFullyClosed","Menu_onMove");
 //BA.debugLineNum = 63;BA.debugLine="lvMenu.Initialize(\"lvMenu\")";
mostCurrent._lvmenu.Initialize(mostCurrent.activityBA,"lvMenu");
 //BA.debugLineNum = 64;BA.debugLine="lvMenu.AddSingleLine(\"Building By Occupants\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Building By Occupants"));
 //BA.debugLineNum = 65;BA.debugLine="lvMenu.AddSingleLine(\"Building By No House\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Building By No House"));
 //BA.debugLineNum = 66;BA.debugLine="lvMenu.AddSingleLine(\"Status And Condition\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Status And Condition"));
 //BA.debugLineNum = 68;BA.debugLine="lvMenu.SingleLineLayout.Label.TextColor = Colors.";
mostCurrent._lvmenu.getSingleLineLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 69;BA.debugLine="lvMenu.Color = Colors.Transparent";
mostCurrent._lvmenu.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 70;BA.debugLine="lvMenu.ScrollingBackgroundColor = Colors.Transpar";
mostCurrent._lvmenu.setScrollingBackgroundColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 71;BA.debugLine="PanelWithSidebar.Sidebar.AddView(lvMenu, 20dip, 2";
mostCurrent._panelwithsidebar._sidebar().AddView((android.view.View)(mostCurrent._lvmenu.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),(int) (-1),(int) (-1));
 //BA.debugLineNum = 80;BA.debugLine="Webview1.Initialize(\"\")";
mostCurrent._webview1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 81;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/laye";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php");
 //BA.debugLineNum = 82;BA.debugLine="PanelWithSidebar.ContentPanel.AddView(Webview1, 0";
mostCurrent._panelwithsidebar._contentpanel().AddView((android.view.View)(mostCurrent._webview1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0))),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 //BA.debugLineNum = 84;BA.debugLine="btnMenu.Initialize(\"\")";
mostCurrent._btnmenu.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 85;BA.debugLine="btnMenu.SetBackgroundImage(LoadBitmap(File.DirAss";
mostCurrent._btnmenu.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"menu.png").getObject()));
 //BA.debugLineNum = 86;BA.debugLine="FakeActionBar.AddView(btnMenu, 100%x - BarSize, 0";
mostCurrent._fakeactionbar.AddView((android.view.View)(mostCurrent._btnmenu.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-_barsize),(int) (0),_barsize,_barsize);
 //BA.debugLineNum = 87;BA.debugLine="PanelWithSidebar.SetOpenCloseButton(btnMenu)";
mostCurrent._panelwithsidebar._setopenclosebutton((anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(mostCurrent._btnmenu.getObject())));
 //BA.debugLineNum = 90;BA.debugLine="Activity.LoadLayout(\"building\")";
mostCurrent._activity.LoadLayout("building",mostCurrent.activityBA);
 //BA.debugLineNum = 93;BA.debugLine="Panel1.Width=Activity.Width";
mostCurrent._panel1.setWidth(mostCurrent._activity.getWidth());
 //BA.debugLineNum = 94;BA.debugLine="Panel1.Height=Activity.Height/3*2";
mostCurrent._panel1.setHeight((int) (mostCurrent._activity.getHeight()/(double)3*2));
 //BA.debugLineNum = 97;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 98;BA.debugLine="Panel3.Visible=False";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 99;BA.debugLine="Panel5.Visible=False";
mostCurrent._panel5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 102;BA.debugLine="Panel3.Height=Panel1.Height";
mostCurrent._panel3.setHeight(mostCurrent._panel1.getHeight());
 //BA.debugLineNum = 103;BA.debugLine="Panel3.Width=Panel1.Width";
mostCurrent._panel3.setWidth(mostCurrent._panel1.getWidth());
 //BA.debugLineNum = 104;BA.debugLine="Panel3.Left=Panel1.Left";
mostCurrent._panel3.setLeft(mostCurrent._panel1.getLeft());
 //BA.debugLineNum = 105;BA.debugLine="Panel5.Width=Panel1.Width";
mostCurrent._panel5.setWidth(mostCurrent._panel1.getWidth());
 //BA.debugLineNum = 106;BA.debugLine="Panel5.Left=Panel1.Left";
mostCurrent._panel5.setLeft(mostCurrent._panel1.getLeft());
 //BA.debugLineNum = 112;BA.debugLine="Spinner1map.Initialize";
mostCurrent._spinner1map.Initialize();
 //BA.debugLineNum = 113;BA.debugLine="Spinner2map.Initialize";
mostCurrent._spinner2map.Initialize();
 //BA.debugLineNum = 114;BA.debugLine="Spinner1.DropdownBackgroundColor=Colors.White";
mostCurrent._spinner1.setDropdownBackgroundColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 115;BA.debugLine="Spinner1.Add(\"All\")";
mostCurrent._spinner1.Add("All");
 //BA.debugLineNum = 116;BA.debugLine="Spinner1map.Put(\"All\",\"0\")";
mostCurrent._spinner1map.Put((Object)("All"),(Object)("0"));
 //BA.debugLineNum = 117;BA.debugLine="Spinner1.Add(\"Non Active\")";
mostCurrent._spinner1.Add("Non Active");
 //BA.debugLineNum = 118;BA.debugLine="Spinner1map.Put(\"Non Active\",\"1\")";
mostCurrent._spinner1map.Put((Object)("Non Active"),(Object)("1"));
 //BA.debugLineNum = 119;BA.debugLine="Spinner1.Add(\"Traditional Heritage\")";
mostCurrent._spinner1.Add("Traditional Heritage");
 //BA.debugLineNum = 120;BA.debugLine="Spinner1map.Put(\"Traditional Heritage\",\"2\")";
mostCurrent._spinner1map.Put((Object)("Traditional Heritage"),(Object)("2"));
 //BA.debugLineNum = 121;BA.debugLine="Spinner1.Add(\"Civil Heritage\")";
mostCurrent._spinner1.Add("Civil Heritage");
 //BA.debugLineNum = 122;BA.debugLine="Spinner1map.Put(\"Civil Heritage\",\"3\")";
mostCurrent._spinner1map.Put((Object)("Civil Heritage"),(Object)("3"));
 //BA.debugLineNum = 123;BA.debugLine="Spinner1.Add(\"Islamic Heritage\")";
mostCurrent._spinner1.Add("Islamic Heritage");
 //BA.debugLineNum = 124;BA.debugLine="Spinner1map.Put(\"Islamic Heritage\",\"4\")";
mostCurrent._spinner1map.Put((Object)("Islamic Heritage"),(Object)("4"));
 //BA.debugLineNum = 125;BA.debugLine="Spinner1.Add(\"Private Property\")";
mostCurrent._spinner1.Add("Private Property");
 //BA.debugLineNum = 126;BA.debugLine="Spinner1map.Put(\"Private Property\",\"5\")";
mostCurrent._spinner1map.Put((Object)("Private Property"),(Object)("5"));
 //BA.debugLineNum = 130;BA.debugLine="Spinner2.DropdownBackgroundColor=Colors.White";
mostCurrent._spinner2.setDropdownBackgroundColor(anywheresoftware.b4a.keywords.Common.Colors.White);
 //BA.debugLineNum = 131;BA.debugLine="Spinner2.Add(\"All\")";
mostCurrent._spinner2.Add("All");
 //BA.debugLineNum = 132;BA.debugLine="Spinner2map.Put(\"All\",\"0\")";
mostCurrent._spinner2map.Put((Object)("All"),(Object)("0"));
 //BA.debugLineNum = 133;BA.debugLine="Spinner2.Add(\"Semi Permanent\")";
mostCurrent._spinner2.Add("Semi Permanent");
 //BA.debugLineNum = 134;BA.debugLine="Spinner2map.Put(\"Semi Permanent\",\"1\")";
mostCurrent._spinner2map.Put((Object)("Semi Permanent"),(Object)("1"));
 //BA.debugLineNum = 135;BA.debugLine="Spinner2.Add(\"Permanent\")";
mostCurrent._spinner2.Add("Permanent");
 //BA.debugLineNum = 136;BA.debugLine="Spinner2map.Put(\"Permanent\",\"2\")";
mostCurrent._spinner2map.Put((Object)("Permanent"),(Object)("2"));
 //BA.debugLineNum = 139;BA.debugLine="ListView1.SingleLineLayout.Label.TextColor = Colo";
mostCurrent._listview1.getSingleLineLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 140;BA.debugLine="ListView1.SingleLineLayout.Label.TextSize = 14";
mostCurrent._listview1.getSingleLineLayout().Label.setTextSize((float) (14));
 //BA.debugLineNum = 143;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 148;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 149;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 145;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 146;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
String _citizen_id = "";
 //BA.debugLineNum = 336;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 338;BA.debugLine="Dim citizen_id As String";
_citizen_id = "";
 //BA.debugLineNum = 339;BA.debugLine="citizen_id=EditText1.Text";
_citizen_id = mostCurrent._edittext1.getText();
 //BA.debugLineNum = 341;BA.debugLine="If citizen_id=Null Then";
if (_citizen_id== null) { 
 //BA.debugLineNum = 342;BA.debugLine="citizen_id=\"\"";
_citizen_id = "";
 };
 //BA.debugLineNum = 345;BA.debugLine="job2.Initialize(\"building\", Me)";
mostCurrent._job2._initialize(processBA,"building",building.getObject());
 //BA.debugLineNum = 346;BA.debugLine="job2.PostString(domain&\"ta_v2/endpoint/building_b";
mostCurrent._job2._poststring(mostCurrent._domain+"ta_v2/endpoint/building_bo.php","citizen_id="+_citizen_id);
 //BA.debugLineNum = 348;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/buil";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/building_bo.php?citizen_id="+_citizen_id);
 //BA.debugLineNum = 350;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
String _nohouse = "";
 //BA.debugLineNum = 386;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 388;BA.debugLine="Dim nohouse As String";
_nohouse = "";
 //BA.debugLineNum = 389;BA.debugLine="nohouse=EditText2.Text";
_nohouse = mostCurrent._edittext2.getText();
 //BA.debugLineNum = 391;BA.debugLine="job2.Initialize(\"nohouse\", Me)";
mostCurrent._job2._initialize(processBA,"nohouse",building.getObject());
 //BA.debugLineNum = 392;BA.debugLine="job2.PostString(domain&\"ta_v2/endpoint/building_n";
mostCurrent._job2._poststring(mostCurrent._domain+"ta_v2/endpoint/building_no.php","nohouse="+_nohouse);
 //BA.debugLineNum = 394;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/buil";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/building_nov.php?nohouse="+_nohouse);
 //BA.debugLineNum = 398;BA.debugLine="End Sub";
return "";
}
public static String  _button3_click() throws Exception{
 //BA.debugLineNum = 360;BA.debugLine="Sub Button3_Click";
 //BA.debugLineNum = 363;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/cond";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/condition_status.php?status="+mostCurrent._id1+"&condition="+mostCurrent._id2);
 //BA.debugLineNum = 365;BA.debugLine="Log(id1)";
anywheresoftware.b4a.keywords.Common.Log(mostCurrent._id1);
 //BA.debugLineNum = 366;BA.debugLine="Log(id2)";
anywheresoftware.b4a.keywords.Common.Log(mostCurrent._id2);
 //BA.debugLineNum = 369;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 13;BA.debugLine="Dim id1 As String";
mostCurrent._id1 = "";
 //BA.debugLineNum = 14;BA.debugLine="Dim id2 As String";
mostCurrent._id2 = "";
 //BA.debugLineNum = 15;BA.debugLine="id1=\"0\"";
mostCurrent._id1 = "0";
 //BA.debugLineNum = 16;BA.debugLine="id2=\"0\"";
mostCurrent._id2 = "0";
 //BA.debugLineNum = 17;BA.debugLine="Dim FakeActionBar, UnderActionBar As Panel";
mostCurrent._fakeactionbar = new anywheresoftware.b4a.objects.PanelWrapper();
mostCurrent._underactionbar = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 18;BA.debugLine="Dim PanelWithSidebar As ClsSlidingSidebar";
mostCurrent._panelwithsidebar = new b4a.example.clsslidingsidebar();
 //BA.debugLineNum = 19;BA.debugLine="Dim btnMenu As Button";
mostCurrent._btnmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim lvMenu As ListView";
mostCurrent._lvmenu = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 21;BA.debugLine="Dim lblInfo As Label";
mostCurrent._lblinfo = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim Webview1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 23;BA.debugLine="Dim domain As String";
mostCurrent._domain = "";
 //BA.debugLineNum = 24;BA.debugLine="domain=\"http://7a880193.ngrok.io/\"";
mostCurrent._domain = "http://7a880193.ngrok.io/";
 //BA.debugLineNum = 25;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private EditText1 As EditText";
mostCurrent._edittext1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Dim job2 As HttpJob";
mostCurrent._job2 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 29;BA.debugLine="Dim length As Int";
_length = 0;
 //BA.debugLineNum = 32;BA.debugLine="Dim SD As SlidingPanels";
mostCurrent._sd = new b4a.example.slidingpanels();
 //BA.debugLineNum = 33;BA.debugLine="Private Panel3 As Panel";
mostCurrent._panel3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private Panel5 As Panel";
mostCurrent._panel5 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private Spinner1 As Spinner";
mostCurrent._spinner1 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private Spinner2 As Spinner";
mostCurrent._spinner2 = new anywheresoftware.b4a.objects.SpinnerWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private Button3 As Button";
mostCurrent._button3 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Dim Spinner1map As Map";
mostCurrent._spinner1map = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 40;BA.debugLine="Dim Spinner2map As Map";
mostCurrent._spinner2map = new anywheresoftware.b4a.objects.collections.Map();
 //BA.debugLineNum = 42;BA.debugLine="Private EditText2 As EditText";
mostCurrent._edittext2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private ListView1 As ListView";
mostCurrent._listview1 = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 44;BA.debugLine="End Sub";
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
int _lv_size1 = 0;
 //BA.debugLineNum = 197;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 198;BA.debugLine="Log(\"JobName = \" & Job.JobName & \", Success = \" &";
anywheresoftware.b4a.keywords.Common.Log("JobName = "+_job._jobname+", Success = "+BA.ObjectToString(_job._success));
 //BA.debugLineNum = 199;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 200;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._jobname,"building","nohouse")) {
case 0: {
 //BA.debugLineNum = 203;BA.debugLine="Log(Job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 //BA.debugLineNum = 205;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 206;BA.debugLine="parser.Initialize(Job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 207;BA.debugLine="Dim root As Map = parser.NextObject";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root = _parser.NextObject();
 //BA.debugLineNum = 208;BA.debugLine="Dim features As List = root.Get(\"features\")";
_features = new anywheresoftware.b4a.objects.collections.List();
_features.setObject((java.util.List)(_root.Get((Object)("features"))));
 //BA.debugLineNum = 209;BA.debugLine="Log(length)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_length));
 //BA.debugLineNum = 211;BA.debugLine="If length>0 Then";
if (_length>0) { 
 //BA.debugLineNum = 212;BA.debugLine="Do While length>=0";
while (_length>=0) {
 //BA.debugLineNum = 213;BA.debugLine="Log(length)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_length));
 //BA.debugLineNum = 214;BA.debugLine="SD.Panels(length).Visible=False";
mostCurrent._sd._panels[_length].setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 215;BA.debugLine="length=length-1";
_length = (int) (_length-1);
 }
;
 };
 //BA.debugLineNum = 219;BA.debugLine="length=0";
_length = (int) (0);
 //BA.debugLineNum = 220;BA.debugLine="SD.Initialize(\"SD\",300,Activity,Me,False) 'Ini";
mostCurrent._sd._initialize(mostCurrent.activityBA,"SD",(int) (300),(anywheresoftware.b4a.objects.PanelWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.PanelWrapper(), (android.view.ViewGroup)(mostCurrent._activity.getObject())),building.getObject(),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 221;BA.debugLine="SD.ModeLittlePanels(15,50%x,50%x,Activity.Heig";
mostCurrent._sd._modelittlepanels((int) (15),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (50),mostCurrent.activityBA),(int) (mostCurrent._activity.getHeight()/(double)6*5),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),anywheresoftware.b4a.keywords.Common.True);
 //BA.debugLineNum = 222;BA.debugLine="For Each colfeatures As Map In features";
_colfeatures = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group21 = _features;
final int groupLen21 = group21.getSize()
;int index21 = 0;
;
for (; index21 < groupLen21;index21++){
_colfeatures.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group21.Get(index21)));
 //BA.debugLineNum = 223;BA.debugLine="Dim geometry As Map = colfeatures.Get(\"geomet";
_geometry = new anywheresoftware.b4a.objects.collections.Map();
_geometry.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_colfeatures.Get((Object)("geometry"))));
 //BA.debugLineNum = 224;BA.debugLine="Dim coordinates As List = geometry.Get(\"coord";
_coordinates = new anywheresoftware.b4a.objects.collections.List();
_coordinates.setObject((java.util.List)(_geometry.Get((Object)("coordinates"))));
 //BA.debugLineNum = 225;BA.debugLine="For Each colcoordinates As List In coordinate";
_colcoordinates = new anywheresoftware.b4a.objects.collections.List();
{
final anywheresoftware.b4a.BA.IterableList group24 = _coordinates;
final int groupLen24 = group24.getSize()
;int index24 = 0;
;
for (; index24 < groupLen24;index24++){
_colcoordinates.setObject((java.util.List)(group24.Get(index24)));
 //BA.debugLineNum = 226;BA.debugLine="For Each colcolcoordinates As List In colcoo";
_colcolcoordinates = new anywheresoftware.b4a.objects.collections.List();
{
final anywheresoftware.b4a.BA.IterableList group25 = _colcoordinates;
final int groupLen25 = group25.getSize()
;int index25 = 0;
;
for (; index25 < groupLen25;index25++){
_colcolcoordinates.setObject((java.util.List)(group25.Get(index25)));
 //BA.debugLineNum = 227;BA.debugLine="For Each colcolcolcoordinates As List In co";
_colcolcolcoordinates = new anywheresoftware.b4a.objects.collections.List();
{
final anywheresoftware.b4a.BA.IterableList group26 = _colcolcoordinates;
final int groupLen26 = group26.getSize()
;int index26 = 0;
;
for (; index26 < groupLen26;index26++){
_colcolcolcoordinates.setObject((java.util.List)(group26.Get(index26)));
 //BA.debugLineNum = 228;BA.debugLine="For Each colcolcolcolcoordinates As Double";
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
 //BA.debugLineNum = 232;BA.debugLine="length=length+1";
_length = (int) (_length+1);
 }
};
 //BA.debugLineNum = 234;BA.debugLine="Dim Type As String = geometry.Get(\"type\")";
_type = BA.ObjectToString(_geometry.Get((Object)("type")));
 //BA.debugLineNum = 235;BA.debugLine="Dim Type As String = colfeatures.Get(\"type\")";
_type = BA.ObjectToString(_colfeatures.Get((Object)("type")));
 //BA.debugLineNum = 236;BA.debugLine="Dim properties As Map = colfeatures.Get(\"prop";
_properties = new anywheresoftware.b4a.objects.collections.Map();
_properties.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_colfeatures.Get((Object)("properties"))));
 //BA.debugLineNum = 237;BA.debugLine="Dim clan_name As String = properties.Get(\"cla";
_clan_name = BA.ObjectToString(_properties.Get((Object)("clan_name")));
 //BA.debugLineNum = 238;BA.debugLine="Dim citizen_id As String = properties.Get(\"ci";
_citizen_id = BA.ObjectToString(_properties.Get((Object)("citizen_id")));
 //BA.debugLineNum = 239;BA.debugLine="Dim gender As String = properties.Get(\"gender";
_gender = BA.ObjectToString(_properties.Get((Object)("gender")));
 //BA.debugLineNum = 240;BA.debugLine="Dim phone As String = properties.Get(\"phone\")";
_phone = BA.ObjectToString(_properties.Get((Object)("phone")));
 //BA.debugLineNum = 241;BA.debugLine="Dim x As String = properties.Get(\"x\")";
_x = BA.ObjectToString(_properties.Get((Object)("x")));
 //BA.debugLineNum = 242;BA.debugLine="Dim name As String = properties.Get(\"name\")";
_name = BA.ObjectToString(_properties.Get((Object)("name")));
 //BA.debugLineNum = 243;BA.debugLine="Dim y As String = properties.Get(\"y\")";
_y = BA.ObjectToString(_properties.Get((Object)("y")));
 //BA.debugLineNum = 244;BA.debugLine="Dim no_house As String = properties.Get(\"no_h";
_no_house = BA.ObjectToString(_properties.Get((Object)("no_house")));
 //BA.debugLineNum = 245;BA.debugLine="Dim born_date As String = properties.Get(\"bor";
_born_date = BA.ObjectToString(_properties.Get((Object)("born_date")));
 //BA.debugLineNum = 249;BA.debugLine="SD.panels(length).Color = Colors.RGB(Rnd(0,25";
mostCurrent._sd._panels[_length].setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB(anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (256)),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (256)),anywheresoftware.b4a.keywords.Common.Rnd((int) (0),(int) (256))));
 //BA.debugLineNum = 250;BA.debugLine="Dim cvs As Canvas";
_cvs = new anywheresoftware.b4a.objects.drawable.CanvasWrapper();
 //BA.debugLineNum = 251;BA.debugLine="cvs.Initialize(SD.Panels(length))";
_cvs.Initialize((android.view.View)(mostCurrent._sd._panels[_length].getObject()));
 //BA.debugLineNum = 252;BA.debugLine="cvs.DrawText(\"House: \"&no_house,SD.panels(len";
_cvs.DrawText(mostCurrent.activityBA,"House: "+_no_house,(float) (mostCurrent._sd._panels[_length].getWidth()/(double)3),(float) (mostCurrent._sd._panels[_length].getHeight()/(double)3),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (5),mostCurrent.activityBA)/(double)anywheresoftware.b4a.keywords.Common.Density),anywheresoftware.b4a.keywords.Common.Colors.White,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 253;BA.debugLine="cvs.DrawText(x,SD.panels(length).Width/3*2,SD";
_cvs.DrawText(mostCurrent.activityBA,_x,(float) (mostCurrent._sd._panels[_length].getWidth()/(double)3*2),(float) (mostCurrent._sd._panels[_length].getHeight()/(double)3+40),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (3),mostCurrent.activityBA)/(double)anywheresoftware.b4a.keywords.Common.Density),anywheresoftware.b4a.keywords.Common.Colors.White,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 254;BA.debugLine="cvs.DrawText(y,SD.panels(length).Width/3*2,SD";
_cvs.DrawText(mostCurrent.activityBA,_y,(float) (mostCurrent._sd._panels[_length].getWidth()/(double)3*2),(float) (mostCurrent._sd._panels[_length].getHeight()/(double)3+10),anywheresoftware.b4a.keywords.Common.Typeface.DEFAULT,(float) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (3),mostCurrent.activityBA)/(double)anywheresoftware.b4a.keywords.Common.Density),anywheresoftware.b4a.keywords.Common.Colors.White,BA.getEnumFromString(android.graphics.Paint.Align.class,"CENTER"));
 //BA.debugLineNum = 256;BA.debugLine="Log(no_house&x&y)";
anywheresoftware.b4a.keywords.Common.Log(_no_house+_x+_y);
 }
};
 //BA.debugLineNum = 258;BA.debugLine="Dim Type As String = root.Get(\"type\")";
_type = BA.ObjectToString(_root.Get((Object)("type")));
 //BA.debugLineNum = 270;BA.debugLine="SD.Start(0) 'Start the SlidingPanels.";
mostCurrent._sd._start((int) (0));
 break; }
case 1: {
 //BA.debugLineNum = 274;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 275;BA.debugLine="parser.Initialize(Job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 276;BA.debugLine="Dim root As Map = parser.NextObject";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root = _parser.NextObject();
 //BA.debugLineNum = 277;BA.debugLine="Dim features As List = root.Get(\"features\")";
_features = new anywheresoftware.b4a.objects.collections.List();
_features.setObject((java.util.List)(_root.Get((Object)("features"))));
 //BA.debugLineNum = 278;BA.debugLine="For Each colfeatures As Map In features";
_colfeatures = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group60 = _features;
final int groupLen60 = group60.getSize()
;int index60 = 0;
;
for (; index60 < groupLen60;index60++){
_colfeatures.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group60.Get(index60)));
 //BA.debugLineNum = 279;BA.debugLine="Dim geometry As Map = colfeatures.Get(\"geomet";
_geometry = new anywheresoftware.b4a.objects.collections.Map();
_geometry.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_colfeatures.Get((Object)("geometry"))));
 //BA.debugLineNum = 280;BA.debugLine="Dim coordinates As List = geometry.Get(\"coord";
_coordinates = new anywheresoftware.b4a.objects.collections.List();
_coordinates.setObject((java.util.List)(_geometry.Get((Object)("coordinates"))));
 //BA.debugLineNum = 281;BA.debugLine="For Each colcoordinates As List In coordinate";
_colcoordinates = new anywheresoftware.b4a.objects.collections.List();
{
final anywheresoftware.b4a.BA.IterableList group63 = _coordinates;
final int groupLen63 = group63.getSize()
;int index63 = 0;
;
for (; index63 < groupLen63;index63++){
_colcoordinates.setObject((java.util.List)(group63.Get(index63)));
 //BA.debugLineNum = 282;BA.debugLine="For Each colcolcoordinates As List In colcoo";
_colcolcoordinates = new anywheresoftware.b4a.objects.collections.List();
{
final anywheresoftware.b4a.BA.IterableList group64 = _colcoordinates;
final int groupLen64 = group64.getSize()
;int index64 = 0;
;
for (; index64 < groupLen64;index64++){
_colcolcoordinates.setObject((java.util.List)(group64.Get(index64)));
 //BA.debugLineNum = 283;BA.debugLine="For Each colcolcolcoordinates As List In co";
_colcolcolcoordinates = new anywheresoftware.b4a.objects.collections.List();
{
final anywheresoftware.b4a.BA.IterableList group65 = _colcolcoordinates;
final int groupLen65 = group65.getSize()
;int index65 = 0;
;
for (; index65 < groupLen65;index65++){
_colcolcolcoordinates.setObject((java.util.List)(group65.Get(index65)));
 //BA.debugLineNum = 284;BA.debugLine="For Each colcolcolcolcoordinates As Double";
{
final anywheresoftware.b4a.BA.IterableList group66 = _colcolcolcoordinates;
final int groupLen66 = group66.getSize()
;int index66 = 0;
;
for (; index66 < groupLen66;index66++){
_colcolcolcolcoordinates = (double)(BA.ObjectToNumber(group66.Get(index66)));
 }
};
 }
};
 }
};
 }
};
 //BA.debugLineNum = 289;BA.debugLine="Dim Type As String = geometry.Get(\"type\")";
_type = BA.ObjectToString(_geometry.Get((Object)("type")));
 //BA.debugLineNum = 290;BA.debugLine="Dim Type As String = colfeatures.Get(\"type\")";
_type = BA.ObjectToString(_colfeatures.Get((Object)("type")));
 //BA.debugLineNum = 291;BA.debugLine="Dim properties As Map = colfeatures.Get(\"prop";
_properties = new anywheresoftware.b4a.objects.collections.Map();
_properties.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_colfeatures.Get((Object)("properties"))));
 //BA.debugLineNum = 292;BA.debugLine="Dim clan_name As String = properties.Get(\"cla";
_clan_name = BA.ObjectToString(_properties.Get((Object)("clan_name")));
 //BA.debugLineNum = 293;BA.debugLine="Dim citizen_id As String = properties.Get(\"ci";
_citizen_id = BA.ObjectToString(_properties.Get((Object)("citizen_id")));
 //BA.debugLineNum = 294;BA.debugLine="Dim gender As String = properties.Get(\"gender";
_gender = BA.ObjectToString(_properties.Get((Object)("gender")));
 //BA.debugLineNum = 295;BA.debugLine="Dim phone As String = properties.Get(\"phone\")";
_phone = BA.ObjectToString(_properties.Get((Object)("phone")));
 //BA.debugLineNum = 296;BA.debugLine="Dim x As String = properties.Get(\"x\")";
_x = BA.ObjectToString(_properties.Get((Object)("x")));
 //BA.debugLineNum = 297;BA.debugLine="Dim name As String = properties.Get(\"name\")";
_name = BA.ObjectToString(_properties.Get((Object)("name")));
 //BA.debugLineNum = 298;BA.debugLine="Dim y As String = properties.Get(\"y\")";
_y = BA.ObjectToString(_properties.Get((Object)("y")));
 //BA.debugLineNum = 299;BA.debugLine="Dim no_house As String = properties.Get(\"no_h";
_no_house = BA.ObjectToString(_properties.Get((Object)("no_house")));
 //BA.debugLineNum = 300;BA.debugLine="Dim born_date As String = properties.Get(\"bor";
_born_date = BA.ObjectToString(_properties.Get((Object)("born_date")));
 //BA.debugLineNum = 302;BA.debugLine="Dim lv_size1 As Int";
_lv_size1 = 0;
 //BA.debugLineNum = 303;BA.debugLine="lv_size1=ListView1.Size";
_lv_size1 = mostCurrent._listview1.getSize();
 //BA.debugLineNum = 304;BA.debugLine="lv_size1=lv_size1-1";
_lv_size1 = (int) (_lv_size1-1);
 //BA.debugLineNum = 305;BA.debugLine="Log(lv_size1)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_lv_size1));
 //BA.debugLineNum = 306;BA.debugLine="If lv_size1>0 Then";
if (_lv_size1>0) { 
 //BA.debugLineNum = 308;BA.debugLine="Do While lv_size1>=0";
while (_lv_size1>=0) {
 //BA.debugLineNum = 310;BA.debugLine="Log(lv_size1)";
anywheresoftware.b4a.keywords.Common.Log(BA.NumberToString(_lv_size1));
 //BA.debugLineNum = 311;BA.debugLine="ListView1.RemoveAt(lv_size1)";
mostCurrent._listview1.RemoveAt(_lv_size1);
 //BA.debugLineNum = 312;BA.debugLine="lv_size1=lv_size1-1";
_lv_size1 = (int) (_lv_size1-1);
 }
;
 };
 //BA.debugLineNum = 321;BA.debugLine="ListView1.AddSingleLine( \"Name: \"&name&\" \"&\"C";
mostCurrent._listview1.AddSingleLine(BA.ObjectToCharSequence("Name: "+_name+" "+"Citizen Id: "+_citizen_id+" "+"Gender: "+_gender+" "+"Phone: "+_phone+" "+"Clan Name: "+_clan_name+"Born Date: "+_born_date));
 }
};
 //BA.debugLineNum = 324;BA.debugLine="Dim Type As String = root.Get(\"type\")";
_type = BA.ObjectToString(_root.Get((Object)("type")));
 //BA.debugLineNum = 325;BA.debugLine="Log(Job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 break; }
}
;
 }else {
 //BA.debugLineNum = 329;BA.debugLine="Log(\"Error: \" & Job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.Log("Error: "+_job._errormessage);
 //BA.debugLineNum = 330;BA.debugLine="ToastMessageShow(\"Error: \" & Job.ErrorMessage, T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: "+_job._errormessage),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 332;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 333;BA.debugLine="End Sub";
return "";
}
public static String  _lvmenu_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 151;BA.debugLine="Sub lvMenu_ItemClick (Position As Int, Value As Ob";
 //BA.debugLineNum = 152;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/laye";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php");
 //BA.debugLineNum = 154;BA.debugLine="If Position=0 Then";
if (_position==0) { 
 //BA.debugLineNum = 156;BA.debugLine="Panel3.Visible=False";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 157;BA.debugLine="Panel5.Visible=False";
mostCurrent._panel5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 160;BA.debugLine="Panel1.Visible=True";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_position==1) { 
 //BA.debugLineNum = 163;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 164;BA.debugLine="Panel5.Visible=False";
mostCurrent._panel5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 166;BA.debugLine="Panel3.Visible=True";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_position==2) { 
 //BA.debugLineNum = 171;BA.debugLine="Panel3.Visible=False";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 172;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 174;BA.debugLine="Panel5.Visible=True";
mostCurrent._panel5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 179;BA.debugLine="PanelWithSidebar.CloseSidebar";
mostCurrent._panelwithsidebar._closesidebar();
 //BA.debugLineNum = 180;BA.debugLine="End Sub";
return "";
}
public static String  _menu_onfullyclosed() throws Exception{
 //BA.debugLineNum = 186;BA.debugLine="Sub Menu_onFullyClosed";
 //BA.debugLineNum = 187;BA.debugLine="Log(\"FULLY CLOSED\")";
anywheresoftware.b4a.keywords.Common.Log("FULLY CLOSED");
 //BA.debugLineNum = 188;BA.debugLine="End Sub";
return "";
}
public static String  _menu_onfullyopen() throws Exception{
 //BA.debugLineNum = 182;BA.debugLine="Sub Menu_onFullyOpen";
 //BA.debugLineNum = 183;BA.debugLine="Log(\"FULLY OPEN\")";
anywheresoftware.b4a.keywords.Common.Log("FULLY OPEN");
 //BA.debugLineNum = 184;BA.debugLine="End Sub";
return "";
}
public static String  _menu_onmove(boolean _isopening) throws Exception{
 //BA.debugLineNum = 190;BA.debugLine="Sub Menu_onMove(IsOpening As Boolean)";
 //BA.debugLineNum = 191;BA.debugLine="Log(\"MOVE IsOpening=\" & IsOpening)";
anywheresoftware.b4a.keywords.Common.Log("MOVE IsOpening="+BA.ObjectToString(_isopening));
 //BA.debugLineNum = 192;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 7;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
public static String  _sd_click(b4a.example.slidingpanels._touchdata _touchdata) throws Exception{
 //BA.debugLineNum = 355;BA.debugLine="Sub SD_Click (TouchData As TouchData)";
 //BA.debugLineNum = 357;BA.debugLine="ToastMessageShow(\"Clicked on Panel: \"&TouchData.T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Clicked on Panel: "+BA.ObjectToString(_touchdata.Tag)+" / X: "+BA.NumberToString(_touchdata.X)+" / Y: "+BA.NumberToString(_touchdata.Y)),anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 358;BA.debugLine="End Sub";
return "";
}
public static String  _spinner1_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 371;BA.debugLine="Sub Spinner1_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 372;BA.debugLine="id1 = Spinner1map.Get(Value)";
mostCurrent._id1 = BA.ObjectToString(mostCurrent._spinner1map.Get(_value));
 //BA.debugLineNum = 376;BA.debugLine="End Sub";
return "";
}
public static String  _spinner2_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 379;BA.debugLine="Sub Spinner2_ItemClick (Position As Int, Value As";
 //BA.debugLineNum = 381;BA.debugLine="id2 = Spinner2map.Get(Value)";
mostCurrent._id2 = BA.ObjectToString(mostCurrent._spinner2map.Get(_value));
 //BA.debugLineNum = 384;BA.debugLine="End Sub";
return "";
}
}
