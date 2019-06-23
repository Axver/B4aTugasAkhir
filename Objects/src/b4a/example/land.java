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

public class land extends Activity implements B4AActivity{
	public static land mostCurrent;
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
			processBA = new BA(this.getApplicationContext(), null, null, "b4a.example", "b4a.example.land");
			processBA.loadHtSubs(this.getClass());
	        float deviceScale = getApplicationContext().getResources().getDisplayMetrics().density;
	        BALayout.setDeviceScale(deviceScale);
            
		}
		else if (previousOne != null) {
			Activity p = previousOne.get();
			if (p != null && p != this) {
                BA.LogInfo("Killing previous instance (land).");
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
		activityBA = new BA(this, layout, processBA, "b4a.example", "b4a.example.land");
        
        processBA.sharedProcessBA.activityBA = new java.lang.ref.WeakReference<BA>(activityBA);
        anywheresoftware.b4a.objects.ViewWrapper.lastId = 0;
        _activity = new ActivityWrapper(activityBA, "activity");
        anywheresoftware.b4a.Msgbox.isDismissing = false;
        if (BA.isShellModeRuntimeCheck(processBA)) {
			if (isFirst)
				processBA.raiseEvent2(null, true, "SHELL", false);
			processBA.raiseEvent2(null, true, "CREATE", true, "b4a.example.land", processBA, activityBA, _activity, anywheresoftware.b4a.keywords.Common.Density, mostCurrent);
			_activity.reinitializeForShell(activityBA, "activity");
		}
        initializeProcessGlobals();		
        initializeGlobals();
        
        BA.LogInfo("** Activity (land) Create, isFirst = " + isFirst + " **");
        processBA.raiseEvent2(null, true, "activity_create", false, isFirst);
		isFirst = false;
		if (this != mostCurrent)
			return;
        processBA.setActivityPaused(false);
        BA.LogInfo("** Activity (land) Resume **");
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
		return land.class;
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
        BA.LogInfo("** Activity (land) Pause, UserClosed = " + activityBA.activity.isFinishing() + " **");
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
            land mc = mostCurrent;
			if (mc == null || mc != activity.get())
				return;
			processBA.setActivityPaused(false);
            BA.LogInfo("** Activity (land) Resume **");
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
public anywheresoftware.b4a.objects.WebViewWrapper _webview1 = null;
public static String _domain = "";
public anywheresoftware.b4a.objects.PanelWrapper _fakeactionbar = null;
public anywheresoftware.b4a.objects.PanelWrapper _underactionbar = null;
public b4a.example.clsslidingsidebar _panelwithsidebar = null;
public anywheresoftware.b4a.objects.ButtonWrapper _btnmenu = null;
public anywheresoftware.b4a.objects.ListViewWrapper _lvmenu = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel1 = null;
public anywheresoftware.b4a.objects.ButtonWrapper _button1 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel3 = null;
public anywheresoftware.b4a.objects.PanelWrapper _panel5 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext1 = null;
public anywheresoftware.b4a.samples.httputils2.httpjob _job2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label8 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label7 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label6 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label5 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label2 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label3 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label4 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label1 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label12 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label13 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label14 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label16 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label9 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label10 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label11 = null;
public anywheresoftware.b4a.objects.LabelWrapper _label15 = null;
public anywheresoftware.b4a.objects.EditTextWrapper _edittext2 = null;
public anywheresoftware.b4a.samples.httputils2.httputils2service _httputils2service = null;
public b4a.example.main _main = null;
public b4a.example.starter _starter = null;
public b4a.example.menu _menu = null;
public b4a.example.layer _layer = null;
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
int _barsize = 0;
int _lightcyan = 0;
anywheresoftware.b4a.objects.drawable.ColorDrawable _cd = null;
 //BA.debugLineNum = 51;BA.debugLine="Sub Activity_Create(FirstTime As Boolean)";
 //BA.debugLineNum = 53;BA.debugLine="Dim BarSize As Int: BarSize = 60dip";
_barsize = 0;
 //BA.debugLineNum = 53;BA.debugLine="Dim BarSize As Int: BarSize = 60dip";
_barsize = anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (60));
 //BA.debugLineNum = 54;BA.debugLine="FakeActionBar.Initialize(\"\")";
mostCurrent._fakeactionbar.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 55;BA.debugLine="FakeActionBar.Color = Colors.RGB(20, 20, 100) 'Da";
mostCurrent._fakeactionbar.setColor(anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (20),(int) (20),(int) (100)));
 //BA.debugLineNum = 56;BA.debugLine="Activity.AddView(FakeActionBar, 0, 0, 100%x, BarS";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._fakeactionbar.getObject()),(int) (0),(int) (0),anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),_barsize);
 //BA.debugLineNum = 58;BA.debugLine="Dim LightCyan As Int: LightCyan = Colors.RGB(0, 9";
_lightcyan = 0;
 //BA.debugLineNum = 58;BA.debugLine="Dim LightCyan As Int: LightCyan = Colors.RGB(0, 9";
_lightcyan = anywheresoftware.b4a.keywords.Common.Colors.RGB((int) (0),(int) (95),(int) (170));
 //BA.debugLineNum = 59;BA.debugLine="UnderActionBar.Initialize(\"\")";
mostCurrent._underactionbar.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 60;BA.debugLine="UnderActionBar.Color = LightCyan";
mostCurrent._underactionbar.setColor(_lightcyan);
 //BA.debugLineNum = 61;BA.debugLine="Activity.AddView(UnderActionBar, 0, BarSize, 100%";
mostCurrent._activity.AddView((android.view.View)(mostCurrent._underactionbar.getObject()),(int) (0),_barsize,anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (40),mostCurrent.activityBA)-_barsize));
 //BA.debugLineNum = 63;BA.debugLine="PanelWithSidebar.Initialize(UnderActionBar, 190di";
mostCurrent._panelwithsidebar._initialize(mostCurrent.activityBA,mostCurrent._underactionbar,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (190)),(byte) (2),(byte) (1),(int) (500),(int) (500));
 //BA.debugLineNum = 64;BA.debugLine="PanelWithSidebar.ContentPanel.Color = LightCyan";
mostCurrent._panelwithsidebar._contentpanel().setColor(_lightcyan);
 //BA.debugLineNum = 65;BA.debugLine="PanelWithSidebar.Sidebar.Background = PanelWithSi";
mostCurrent._panelwithsidebar._sidebar().setBackground((android.graphics.drawable.Drawable)(mostCurrent._panelwithsidebar._loaddrawable("popup_inline_error")));
 //BA.debugLineNum = 66;BA.debugLine="PanelWithSidebar.SetOnChangeListeners(Me, \"Menu_o";
mostCurrent._panelwithsidebar._setonchangelisteners(land.getObject(),"Menu_onFullyOpen","Menu_onFullyClosed","Menu_onMove");
 //BA.debugLineNum = 68;BA.debugLine="lvMenu.Initialize(\"lvMenu\")";
mostCurrent._lvmenu.Initialize(mostCurrent.activityBA,"lvMenu");
 //BA.debugLineNum = 69;BA.debugLine="lvMenu.AddSingleLine(\"Lands\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Lands"));
 //BA.debugLineNum = 70;BA.debugLine="lvMenu.AddSingleLine(\"Lands By Owner\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Lands By Owner"));
 //BA.debugLineNum = 71;BA.debugLine="lvMenu.AddSingleLine(\"Land By Tax Number\")";
mostCurrent._lvmenu.AddSingleLine(BA.ObjectToCharSequence("Land By Tax Number"));
 //BA.debugLineNum = 73;BA.debugLine="lvMenu.SingleLineLayout.Label.TextColor = Colors.";
mostCurrent._lvmenu.getSingleLineLayout().Label.setTextColor(anywheresoftware.b4a.keywords.Common.Colors.Black);
 //BA.debugLineNum = 74;BA.debugLine="lvMenu.Color = Colors.Transparent";
mostCurrent._lvmenu.setColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 75;BA.debugLine="lvMenu.ScrollingBackgroundColor = Colors.Transpar";
mostCurrent._lvmenu.setScrollingBackgroundColor(anywheresoftware.b4a.keywords.Common.Colors.Transparent);
 //BA.debugLineNum = 76;BA.debugLine="PanelWithSidebar.Sidebar.AddView(lvMenu, 20dip, 2";
mostCurrent._panelwithsidebar._sidebar().AddView((android.view.View)(mostCurrent._lvmenu.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (20)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (25)),(int) (-1),(int) (-1));
 //BA.debugLineNum = 85;BA.debugLine="Webview1.Initialize(\"\")";
mostCurrent._webview1.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 86;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/laye";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php");
 //BA.debugLineNum = 87;BA.debugLine="PanelWithSidebar.ContentPanel.AddView(Webview1, 0";
mostCurrent._panelwithsidebar._contentpanel().AddView((android.view.View)(mostCurrent._webview1.getObject()),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0)),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (0))),(int) (anywheresoftware.b4a.keywords.Common.PerYToCurrent((float) (100),mostCurrent.activityBA)-anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (1))));
 //BA.debugLineNum = 89;BA.debugLine="btnMenu.Initialize(\"\")";
mostCurrent._btnmenu.Initialize(mostCurrent.activityBA,"");
 //BA.debugLineNum = 90;BA.debugLine="btnMenu.SetBackgroundImage(LoadBitmap(File.DirAss";
mostCurrent._btnmenu.SetBackgroundImageNew((android.graphics.Bitmap)(anywheresoftware.b4a.keywords.Common.LoadBitmap(anywheresoftware.b4a.keywords.Common.File.getDirAssets(),"menu.png").getObject()));
 //BA.debugLineNum = 91;BA.debugLine="FakeActionBar.AddView(btnMenu, 100%x - BarSize, 0";
mostCurrent._fakeactionbar.AddView((android.view.View)(mostCurrent._btnmenu.getObject()),(int) (anywheresoftware.b4a.keywords.Common.PerXToCurrent((float) (100),mostCurrent.activityBA)-_barsize),(int) (0),_barsize,_barsize);
 //BA.debugLineNum = 92;BA.debugLine="PanelWithSidebar.SetOpenCloseButton(btnMenu)";
mostCurrent._panelwithsidebar._setopenclosebutton((anywheresoftware.b4a.objects.ConcreteViewWrapper) anywheresoftware.b4a.AbsObjectWrapper.ConvertToWrapper(new anywheresoftware.b4a.objects.ConcreteViewWrapper(), (android.view.View)(mostCurrent._btnmenu.getObject())));
 //BA.debugLineNum = 96;BA.debugLine="Activity.LoadLayout(\"land\")";
mostCurrent._activity.LoadLayout("land",mostCurrent.activityBA);
 //BA.debugLineNum = 99;BA.debugLine="Dim cd As ColorDrawable";
_cd = new anywheresoftware.b4a.objects.drawable.ColorDrawable();
 //BA.debugLineNum = 100;BA.debugLine="cd.Initialize(Colors.Red,5dip)";
_cd.Initialize(anywheresoftware.b4a.keywords.Common.Colors.Red,anywheresoftware.b4a.keywords.Common.DipToCurrent((int) (5)));
 //BA.debugLineNum = 101;BA.debugLine="Label1.Background=cd";
mostCurrent._label1.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 102;BA.debugLine="Label2.Background=cd";
mostCurrent._label2.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 103;BA.debugLine="Label3.Background=cd";
mostCurrent._label3.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 104;BA.debugLine="Label4.Background=cd";
mostCurrent._label4.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 105;BA.debugLine="Label9.Background=cd";
mostCurrent._label9.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 106;BA.debugLine="Label10.Background=cd";
mostCurrent._label10.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 107;BA.debugLine="Label11.Background=cd";
mostCurrent._label11.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 108;BA.debugLine="Label15.Background=cd";
mostCurrent._label15.setBackground((android.graphics.drawable.Drawable)(_cd.getObject()));
 //BA.debugLineNum = 111;BA.debugLine="Label9.Width=Activity.Width/3";
mostCurrent._label9.setWidth((int) (mostCurrent._activity.getWidth()/(double)3));
 //BA.debugLineNum = 112;BA.debugLine="Label10.Width=Activity.Width/3";
mostCurrent._label10.setWidth((int) (mostCurrent._activity.getWidth()/(double)3));
 //BA.debugLineNum = 113;BA.debugLine="Label11.Width=Activity.Width/3";
mostCurrent._label11.setWidth((int) (mostCurrent._activity.getWidth()/(double)3));
 //BA.debugLineNum = 114;BA.debugLine="Label12.Width=Activity.Width/3";
mostCurrent._label12.setWidth((int) (mostCurrent._activity.getWidth()/(double)3));
 //BA.debugLineNum = 115;BA.debugLine="Label13.Width=Activity.Width/3";
mostCurrent._label13.setWidth((int) (mostCurrent._activity.getWidth()/(double)3));
 //BA.debugLineNum = 116;BA.debugLine="Label14.Width=Activity.Width/3";
mostCurrent._label14.setWidth((int) (mostCurrent._activity.getWidth()/(double)3));
 //BA.debugLineNum = 120;BA.debugLine="Panel3.Left=Panel1.Left";
mostCurrent._panel3.setLeft(mostCurrent._panel1.getLeft());
 //BA.debugLineNum = 121;BA.debugLine="Panel5.Left=Panel1.Left";
mostCurrent._panel5.setLeft(mostCurrent._panel1.getLeft());
 //BA.debugLineNum = 124;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 125;BA.debugLine="Panel3.Visible=False";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 126;BA.debugLine="Panel5.Visible=False";
mostCurrent._panel5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 130;BA.debugLine="End Sub";
return "";
}
public static String  _activity_pause(boolean _userclosed) throws Exception{
 //BA.debugLineNum = 136;BA.debugLine="Sub Activity_Pause (UserClosed As Boolean)";
 //BA.debugLineNum = 138;BA.debugLine="End Sub";
return "";
}
public static String  _activity_resume() throws Exception{
 //BA.debugLineNum = 132;BA.debugLine="Sub Activity_Resume";
 //BA.debugLineNum = 134;BA.debugLine="End Sub";
return "";
}
public static String  _button1_click() throws Exception{
 //BA.debugLineNum = 187;BA.debugLine="Sub Button1_Click";
 //BA.debugLineNum = 188;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/alll";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/allland.php");
 //BA.debugLineNum = 189;BA.debugLine="End Sub";
return "";
}
public static String  _button2_click() throws Exception{
String _owner_id = "";
 //BA.debugLineNum = 191;BA.debugLine="Sub Button2_Click";
 //BA.debugLineNum = 192;BA.debugLine="Dim owner_id As String";
_owner_id = "";
 //BA.debugLineNum = 193;BA.debugLine="owner_id=EditText1.Text";
_owner_id = mostCurrent._edittext1.getText();
 //BA.debugLineNum = 194;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/land";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/land_owner.php?owner_id="+_owner_id);
 //BA.debugLineNum = 197;BA.debugLine="job2.Initialize(\"land_owner\", Me)";
mostCurrent._job2._initialize(processBA,"land_owner",land.getObject());
 //BA.debugLineNum = 198;BA.debugLine="job2.PostString(domain&\"ta_v2/endpoint/land_owner";
mostCurrent._job2._poststring(mostCurrent._domain+"ta_v2/endpoint/land_owner.php","owner_id="+_owner_id);
 //BA.debugLineNum = 201;BA.debugLine="End Sub";
return "";
}
public static String  _button3_click() throws Exception{
String _tax_number = "";
 //BA.debugLineNum = 293;BA.debugLine="Sub Button3_Click";
 //BA.debugLineNum = 294;BA.debugLine="Dim tax_number As String";
_tax_number = "";
 //BA.debugLineNum = 295;BA.debugLine="tax_number=EditText2.Text";
_tax_number = mostCurrent._edittext2.getText();
 //BA.debugLineNum = 296;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/land";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/land_tax_view.php?tax_number="+_tax_number);
 //BA.debugLineNum = 299;BA.debugLine="job2.Initialize(\"land_tax\", Me)";
mostCurrent._job2._initialize(processBA,"land_tax",land.getObject());
 //BA.debugLineNum = 300;BA.debugLine="job2.PostString(domain&\"ta_v2/endpoint/land_tax.p";
mostCurrent._job2._poststring(mostCurrent._domain+"ta_v2/endpoint/land_tax.php","tax_number="+_tax_number);
 //BA.debugLineNum = 302;BA.debugLine="End Sub";
return "";
}
public static String  _globals() throws Exception{
 //BA.debugLineNum = 12;BA.debugLine="Sub Globals";
 //BA.debugLineNum = 16;BA.debugLine="Dim Webview1 As WebView";
mostCurrent._webview1 = new anywheresoftware.b4a.objects.WebViewWrapper();
 //BA.debugLineNum = 17;BA.debugLine="Dim domain As String";
mostCurrent._domain = "";
 //BA.debugLineNum = 18;BA.debugLine="domain=\"http://9adecea0.ngrok.io/\"";
mostCurrent._domain = "http://9adecea0.ngrok.io/";
 //BA.debugLineNum = 19;BA.debugLine="Dim FakeActionBar, UnderActionBar As Panel";
mostCurrent._fakeactionbar = new anywheresoftware.b4a.objects.PanelWrapper();
mostCurrent._underactionbar = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 20;BA.debugLine="Dim PanelWithSidebar As ClsSlidingSidebar";
mostCurrent._panelwithsidebar = new b4a.example.clsslidingsidebar();
 //BA.debugLineNum = 21;BA.debugLine="Dim btnMenu As Button";
mostCurrent._btnmenu = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 22;BA.debugLine="Dim lvMenu As ListView";
mostCurrent._lvmenu = new anywheresoftware.b4a.objects.ListViewWrapper();
 //BA.debugLineNum = 24;BA.debugLine="Private Panel1 As Panel";
mostCurrent._panel1 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 25;BA.debugLine="Private Button1 As Button";
mostCurrent._button1 = new anywheresoftware.b4a.objects.ButtonWrapper();
 //BA.debugLineNum = 26;BA.debugLine="Private Panel3 As Panel";
mostCurrent._panel3 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 27;BA.debugLine="Private Panel5 As Panel";
mostCurrent._panel5 = new anywheresoftware.b4a.objects.PanelWrapper();
 //BA.debugLineNum = 28;BA.debugLine="Private EditText1 As EditText";
mostCurrent._edittext1 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 31;BA.debugLine="Dim job2 As HttpJob";
mostCurrent._job2 = new anywheresoftware.b4a.samples.httputils2.httpjob();
 //BA.debugLineNum = 32;BA.debugLine="Private Label8 As Label";
mostCurrent._label8 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 33;BA.debugLine="Private Label7 As Label";
mostCurrent._label7 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 34;BA.debugLine="Private Label6 As Label";
mostCurrent._label6 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 35;BA.debugLine="Private Label5 As Label";
mostCurrent._label5 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 36;BA.debugLine="Private Label2 As Label";
mostCurrent._label2 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 37;BA.debugLine="Private Label3 As Label";
mostCurrent._label3 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 38;BA.debugLine="Private Label4 As Label";
mostCurrent._label4 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 39;BA.debugLine="Private Label1 As Label";
mostCurrent._label1 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 40;BA.debugLine="Private Label12 As Label";
mostCurrent._label12 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 41;BA.debugLine="Private Label13 As Label";
mostCurrent._label13 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 42;BA.debugLine="Private Label14 As Label";
mostCurrent._label14 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 43;BA.debugLine="Private Label16 As Label";
mostCurrent._label16 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 44;BA.debugLine="Private Label9 As Label";
mostCurrent._label9 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 45;BA.debugLine="Private Label10 As Label";
mostCurrent._label10 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 46;BA.debugLine="Private Label11 As Label";
mostCurrent._label11 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 47;BA.debugLine="Private Label15 As Label";
mostCurrent._label15 = new anywheresoftware.b4a.objects.LabelWrapper();
 //BA.debugLineNum = 48;BA.debugLine="Private EditText2 As EditText";
mostCurrent._edittext2 = new anywheresoftware.b4a.objects.EditTextWrapper();
 //BA.debugLineNum = 49;BA.debugLine="End Sub";
return "";
}
public static String  _jobdone(anywheresoftware.b4a.samples.httputils2.httpjob _job) throws Exception{
anywheresoftware.b4a.objects.collections.JSONParser _parser = null;
anywheresoftware.b4a.objects.collections.Map _root = null;
anywheresoftware.b4a.objects.collections.List _features = null;
anywheresoftware.b4a.objects.collections.Map _colfeatures = null;
anywheresoftware.b4a.objects.collections.Map _properties = null;
String _clan_name = "";
String _nik = "";
String _citizen_name = "";
String _address = "";
String _gender = "";
String _phone = "";
String _status_name = "";
String _born_date = "";
String _type = "";
anywheresoftware.b4a.objects.collections.Map _geometry = null;
anywheresoftware.b4a.objects.collections.List _coordinates = null;
anywheresoftware.b4a.objects.collections.List _colcoordinates = null;
anywheresoftware.b4a.objects.collections.List _colcolcoordinates = null;
anywheresoftware.b4a.objects.collections.List _colcolcolcoordinates = null;
double _colcolcolcolcoordinates = 0;
String _owner_name = "";
String _land_owner = "";
String _tax_number = "";
String _x = "";
String _y = "";
String _land_id = "";
 //BA.debugLineNum = 207;BA.debugLine="Sub JobDone (Job As HttpJob)";
 //BA.debugLineNum = 209;BA.debugLine="Log(\"JobName = \" & Job.JobName & \", Success = \" &";
anywheresoftware.b4a.keywords.Common.Log("JobName = "+_job._jobname+", Success = "+BA.ObjectToString(_job._success));
 //BA.debugLineNum = 210;BA.debugLine="If Job.Success = True Then";
if (_job._success==anywheresoftware.b4a.keywords.Common.True) { 
 //BA.debugLineNum = 211;BA.debugLine="Select Job.JobName";
switch (BA.switchObjectToInt(_job._jobname,"land_owner","land_tax")) {
case 0: {
 //BA.debugLineNum = 213;BA.debugLine="Log(Job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 //BA.debugLineNum = 216;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 217;BA.debugLine="parser.Initialize(Job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 218;BA.debugLine="Dim root As Map = parser.NextObject";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root = _parser.NextObject();
 //BA.debugLineNum = 219;BA.debugLine="Dim features As List = root.Get(\"features\")";
_features = new anywheresoftware.b4a.objects.collections.List();
_features.setObject((java.util.List)(_root.Get((Object)("features"))));
 //BA.debugLineNum = 220;BA.debugLine="For Each colfeatures As Map In features";
_colfeatures = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group10 = _features;
final int groupLen10 = group10.getSize()
;int index10 = 0;
;
for (; index10 < groupLen10;index10++){
_colfeatures.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group10.Get(index10)));
 //BA.debugLineNum = 221;BA.debugLine="Dim properties As Map = colfeatures.Get(\"prop";
_properties = new anywheresoftware.b4a.objects.collections.Map();
_properties.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_colfeatures.Get((Object)("properties"))));
 //BA.debugLineNum = 222;BA.debugLine="Dim clan_name As String = properties.Get(\"cla";
_clan_name = BA.ObjectToString(_properties.Get((Object)("clan_name")));
 //BA.debugLineNum = 223;BA.debugLine="Dim nik As String = properties.Get(\"nik\")";
_nik = BA.ObjectToString(_properties.Get((Object)("nik")));
 //BA.debugLineNum = 224;BA.debugLine="Dim citizen_name As String = properties.Get(\"";
_citizen_name = BA.ObjectToString(_properties.Get((Object)("citizen_name")));
 //BA.debugLineNum = 225;BA.debugLine="Dim address As String = properties.Get(\"addre";
_address = BA.ObjectToString(_properties.Get((Object)("address")));
 //BA.debugLineNum = 226;BA.debugLine="Dim gender As String = properties.Get(\"gender";
_gender = BA.ObjectToString(_properties.Get((Object)("gender")));
 //BA.debugLineNum = 227;BA.debugLine="Dim phone As String = properties.Get(\"phone\")";
_phone = BA.ObjectToString(_properties.Get((Object)("phone")));
 //BA.debugLineNum = 228;BA.debugLine="Dim status_name As String = properties.Get(\"s";
_status_name = BA.ObjectToString(_properties.Get((Object)("status_name")));
 //BA.debugLineNum = 230;BA.debugLine="Dim born_date As String = properties.Get(\"bor";
_born_date = BA.ObjectToString(_properties.Get((Object)("born_date")));
 //BA.debugLineNum = 233;BA.debugLine="Label8.Text=nik";
mostCurrent._label8.setText(BA.ObjectToCharSequence(_nik));
 //BA.debugLineNum = 234;BA.debugLine="Label7.Text=citizen_name";
mostCurrent._label7.setText(BA.ObjectToCharSequence(_citizen_name));
 //BA.debugLineNum = 235;BA.debugLine="Label6.Text=clan_name";
mostCurrent._label6.setText(BA.ObjectToCharSequence(_clan_name));
 //BA.debugLineNum = 236;BA.debugLine="Label5.Text=gender";
mostCurrent._label5.setText(BA.ObjectToCharSequence(_gender));
 //BA.debugLineNum = 238;BA.debugLine="Label12.Text=phone";
mostCurrent._label12.setText(BA.ObjectToCharSequence(_phone));
 //BA.debugLineNum = 239;BA.debugLine="Label13.Text=status_name";
mostCurrent._label13.setText(BA.ObjectToCharSequence(_status_name));
 //BA.debugLineNum = 240;BA.debugLine="Label14.Text=born_date";
mostCurrent._label14.setText(BA.ObjectToCharSequence(_born_date));
 //BA.debugLineNum = 241;BA.debugLine="Label16.Text=address";
mostCurrent._label16.setText(BA.ObjectToCharSequence(_address));
 //BA.debugLineNum = 245;BA.debugLine="Label7.Left=Label2.Left";
mostCurrent._label7.setLeft(mostCurrent._label2.getLeft());
 //BA.debugLineNum = 246;BA.debugLine="Label6.Left=Label3.Left";
mostCurrent._label6.setLeft(mostCurrent._label3.getLeft());
 //BA.debugLineNum = 247;BA.debugLine="Label5.Left=Label4.Left";
mostCurrent._label5.setLeft(mostCurrent._label4.getLeft());
 }
};
 //BA.debugLineNum = 249;BA.debugLine="Dim Type As String = root.Get(\"type\")";
_type = BA.ObjectToString(_root.Get((Object)("type")));
 break; }
case 1: {
 //BA.debugLineNum = 252;BA.debugLine="Log(Job.GetString)";
anywheresoftware.b4a.keywords.Common.Log(_job._getstring());
 //BA.debugLineNum = 253;BA.debugLine="Dim parser As JSONParser";
_parser = new anywheresoftware.b4a.objects.collections.JSONParser();
 //BA.debugLineNum = 254;BA.debugLine="parser.Initialize(Job.GetString)";
_parser.Initialize(_job._getstring());
 //BA.debugLineNum = 255;BA.debugLine="Dim root As Map = parser.NextObject";
_root = new anywheresoftware.b4a.objects.collections.Map();
_root = _parser.NextObject();
 //BA.debugLineNum = 256;BA.debugLine="Dim features As List = root.Get(\"features\")";
_features = new anywheresoftware.b4a.objects.collections.List();
_features.setObject((java.util.List)(_root.Get((Object)("features"))));
 //BA.debugLineNum = 257;BA.debugLine="For Each colfeatures As Map In features";
_colfeatures = new anywheresoftware.b4a.objects.collections.Map();
{
final anywheresoftware.b4a.BA.IterableList group39 = _features;
final int groupLen39 = group39.getSize()
;int index39 = 0;
;
for (; index39 < groupLen39;index39++){
_colfeatures.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(group39.Get(index39)));
 //BA.debugLineNum = 258;BA.debugLine="Dim geometry As Map = colfeatures.Get(\"geomet";
_geometry = new anywheresoftware.b4a.objects.collections.Map();
_geometry.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_colfeatures.Get((Object)("geometry"))));
 //BA.debugLineNum = 259;BA.debugLine="Dim coordinates As List = geometry.Get(\"coord";
_coordinates = new anywheresoftware.b4a.objects.collections.List();
_coordinates.setObject((java.util.List)(_geometry.Get((Object)("coordinates"))));
 //BA.debugLineNum = 260;BA.debugLine="For Each colcoordinates As List In coordinate";
_colcoordinates = new anywheresoftware.b4a.objects.collections.List();
{
final anywheresoftware.b4a.BA.IterableList group42 = _coordinates;
final int groupLen42 = group42.getSize()
;int index42 = 0;
;
for (; index42 < groupLen42;index42++){
_colcoordinates.setObject((java.util.List)(group42.Get(index42)));
 //BA.debugLineNum = 261;BA.debugLine="For Each colcolcoordinates As List In colcoo";
_colcolcoordinates = new anywheresoftware.b4a.objects.collections.List();
{
final anywheresoftware.b4a.BA.IterableList group43 = _colcoordinates;
final int groupLen43 = group43.getSize()
;int index43 = 0;
;
for (; index43 < groupLen43;index43++){
_colcolcoordinates.setObject((java.util.List)(group43.Get(index43)));
 //BA.debugLineNum = 262;BA.debugLine="For Each colcolcolcoordinates As List In co";
_colcolcolcoordinates = new anywheresoftware.b4a.objects.collections.List();
{
final anywheresoftware.b4a.BA.IterableList group44 = _colcolcoordinates;
final int groupLen44 = group44.getSize()
;int index44 = 0;
;
for (; index44 < groupLen44;index44++){
_colcolcolcoordinates.setObject((java.util.List)(group44.Get(index44)));
 //BA.debugLineNum = 263;BA.debugLine="For Each colcolcolcolcoordinates As Double";
{
final anywheresoftware.b4a.BA.IterableList group45 = _colcolcolcoordinates;
final int groupLen45 = group45.getSize()
;int index45 = 0;
;
for (; index45 < groupLen45;index45++){
_colcolcolcolcoordinates = (double)(BA.ObjectToNumber(group45.Get(index45)));
 }
};
 }
};
 }
};
 }
};
 //BA.debugLineNum = 268;BA.debugLine="Dim Type As String = geometry.Get(\"type\")";
_type = BA.ObjectToString(_geometry.Get((Object)("type")));
 //BA.debugLineNum = 269;BA.debugLine="Dim Type As String = colfeatures.Get(\"tyTypep";
_type = BA.ObjectToString(_colfeatures.Get((Object)("tyTypepe")));
 //BA.debugLineNum = 270;BA.debugLine="Dim properties As Map = colfeatures.Get(\"prop";
_properties = new anywheresoftware.b4a.objects.collections.Map();
_properties.setObject((anywheresoftware.b4a.objects.collections.Map.MyMap)(_colfeatures.Get((Object)("properties"))));
 //BA.debugLineNum = 271;BA.debugLine="Dim clan_name As String = properties.Get(\"cla";
_clan_name = BA.ObjectToString(_properties.Get((Object)("clan_name")));
 //BA.debugLineNum = 272;BA.debugLine="Dim owner_name As String = properties.Get(\"ow";
_owner_name = BA.ObjectToString(_properties.Get((Object)("owner_name")));
 //BA.debugLineNum = 273;BA.debugLine="Dim gender As String = properties.Get(\"gender";
_gender = BA.ObjectToString(_properties.Get((Object)("gender")));
 //BA.debugLineNum = 274;BA.debugLine="Dim land_owner As String = properties.Get(\"la";
_land_owner = BA.ObjectToString(_properties.Get((Object)("land_owner")));
 //BA.debugLineNum = 275;BA.debugLine="Dim phone As String = properties.Get(\"phone\")";
_phone = BA.ObjectToString(_properties.Get((Object)("phone")));
 //BA.debugLineNum = 276;BA.debugLine="Dim tax_number As String = properties.Get(\"ta";
_tax_number = BA.ObjectToString(_properties.Get((Object)("tax_number")));
 //BA.debugLineNum = 277;BA.debugLine="Dim status_name As String = properties.Get(\"s";
_status_name = BA.ObjectToString(_properties.Get((Object)("status_name")));
 //BA.debugLineNum = 278;BA.debugLine="Dim x As String = properties.Get(\"x\")";
_x = BA.ObjectToString(_properties.Get((Object)("x")));
 //BA.debugLineNum = 279;BA.debugLine="Dim y As String = properties.Get(\"y\")";
_y = BA.ObjectToString(_properties.Get((Object)("y")));
 //BA.debugLineNum = 280;BA.debugLine="Dim land_id As String = properties.Get(\"land_";
_land_id = BA.ObjectToString(_properties.Get((Object)("land_id")));
 //BA.debugLineNum = 281;BA.debugLine="Dim born_date As String = properties.Get(\"bor";
_born_date = BA.ObjectToString(_properties.Get((Object)("born_date")));
 }
};
 //BA.debugLineNum = 283;BA.debugLine="Dim Type As String = root.Get(\"type\")";
_type = BA.ObjectToString(_root.Get((Object)("type")));
 break; }
}
;
 }else {
 //BA.debugLineNum = 287;BA.debugLine="Log(\"Error: \" & Job.ErrorMessage)";
anywheresoftware.b4a.keywords.Common.Log("Error: "+_job._errormessage);
 //BA.debugLineNum = 288;BA.debugLine="ToastMessageShow(\"Error: \" & Job.ErrorMessage, T";
anywheresoftware.b4a.keywords.Common.ToastMessageShow(BA.ObjectToCharSequence("Error: "+_job._errormessage),anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 290;BA.debugLine="Job.Release";
_job._release();
 //BA.debugLineNum = 291;BA.debugLine="End Sub";
return "";
}
public static String  _lvmenu_itemclick(int _position,Object _value) throws Exception{
 //BA.debugLineNum = 142;BA.debugLine="Sub lvMenu_ItemClick (Position As Int, Value As Ob";
 //BA.debugLineNum = 143;BA.debugLine="Webview1.LoadUrl(domain&\"ta_v2/endpoint/view/laye";
mostCurrent._webview1.LoadUrl(mostCurrent._domain+"ta_v2/endpoint/view/layers.php");
 //BA.debugLineNum = 146;BA.debugLine="If Position=0 Then";
if (_position==0) { 
 //BA.debugLineNum = 148;BA.debugLine="Panel3.Visible=False";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 149;BA.debugLine="Panel5.Visible=False";
mostCurrent._panel5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 151;BA.debugLine="Panel1.Visible=True";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_position==1) { 
 //BA.debugLineNum = 155;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 156;BA.debugLine="Panel5.Visible=False";
mostCurrent._panel5.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 159;BA.debugLine="Panel3.Visible=True";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.True);
 }else if(_position==2) { 
 //BA.debugLineNum = 163;BA.debugLine="Panel1.Visible=False";
mostCurrent._panel1.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 164;BA.debugLine="Panel3.Visible=False";
mostCurrent._panel3.setVisible(anywheresoftware.b4a.keywords.Common.False);
 //BA.debugLineNum = 166;BA.debugLine="Panel5.Visible=True";
mostCurrent._panel5.setVisible(anywheresoftware.b4a.keywords.Common.True);
 };
 //BA.debugLineNum = 170;BA.debugLine="PanelWithSidebar.CloseSidebar";
mostCurrent._panelwithsidebar._closesidebar();
 //BA.debugLineNum = 171;BA.debugLine="End Sub";
return "";
}
public static String  _menu_onfullyclosed() throws Exception{
 //BA.debugLineNum = 177;BA.debugLine="Sub Menu_onFullyClosed";
 //BA.debugLineNum = 179;BA.debugLine="End Sub";
return "";
}
public static String  _menu_onfullyopen() throws Exception{
 //BA.debugLineNum = 173;BA.debugLine="Sub Menu_onFullyOpen";
 //BA.debugLineNum = 175;BA.debugLine="End Sub";
return "";
}
public static String  _menu_onmove(boolean _isopening) throws Exception{
 //BA.debugLineNum = 181;BA.debugLine="Sub Menu_onMove(IsOpening As Boolean)";
 //BA.debugLineNum = 183;BA.debugLine="End Sub";
return "";
}
public static String  _process_globals() throws Exception{
 //BA.debugLineNum = 6;BA.debugLine="Sub Process_Globals";
 //BA.debugLineNum = 10;BA.debugLine="End Sub";
return "";
}
}
