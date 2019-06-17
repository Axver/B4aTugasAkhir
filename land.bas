B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=8.3
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: False
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	
	Dim Webview1 As WebView
	Dim domain As String
	domain="http://7a880193.ngrok.io/"
	Dim FakeActionBar, UnderActionBar As Panel
	Dim PanelWithSidebar As ClsSlidingSidebar
	Dim btnMenu As Button
	Dim lvMenu As ListView

	Private Panel1 As Panel
	Private Button1 As Button
	Private Panel3 As Panel
	Private Panel5 As Panel
	Private EditText1 As EditText
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	Dim BarSize As Int: BarSize = 60dip
	FakeActionBar.Initialize("")
	FakeActionBar.Color = Colors.RGB(20, 20, 100) 'Dark blue
	Activity.AddView(FakeActionBar, 0, 0, 100%x, BarSize)
	
	Dim LightCyan As Int: LightCyan = Colors.RGB(0, 95, 170)
	UnderActionBar.Initialize("")
	UnderActionBar.Color = LightCyan
	Activity.AddView(UnderActionBar, 0, BarSize, 100%x, 40%y - BarSize)
	
	PanelWithSidebar.Initialize(UnderActionBar, 190dip, 2, 1, 500, 500)
	PanelWithSidebar.ContentPanel.Color = LightCyan
	PanelWithSidebar.Sidebar.Background = PanelWithSidebar.LoadDrawable("popup_inline_error")
	PanelWithSidebar.SetOnChangeListeners(Me, "Menu_onFullyOpen", "Menu_onFullyClosed", "Menu_onMove")

	lvMenu.Initialize("lvMenu")
	lvMenu.AddSingleLine("Lands")
	lvMenu.AddSingleLine("Lands By Owner")
	lvMenu.AddSingleLine("Land By Tax Number")
	
	lvMenu.SingleLineLayout.Label.TextColor = Colors.Black
	lvMenu.Color = Colors.Transparent
	lvMenu.ScrollingBackgroundColor = Colors.Transparent
	PanelWithSidebar.Sidebar.AddView(lvMenu, 20dip, 25dip, -1, -1)

	'lblInfo.Initialize("")
	'lblInfo.Text = "Click the button to open/close the sliding menu."
	'lblInfo.TextColor = Colors.Black
	'lblInfo.TextSize = 24
	'PanelWithSidebar.ContentPanel.AddView(lblInfo, 30dip, 30dip, 100%x - 60dip, 100%y - 60dip)
	
	
	Webview1.Initialize("")
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php")
	PanelWithSidebar.ContentPanel.AddView(Webview1, 0dip, 0dip, 100%x - 0dip, 100%y - 1dip)

	btnMenu.Initialize("")
	btnMenu.SetBackgroundImage(LoadBitmap(File.DirAssets, "menu.png"))
	FakeActionBar.AddView(btnMenu, 100%x - BarSize, 0, BarSize, BarSize)
	PanelWithSidebar.SetOpenCloseButton(btnMenu)
	
	

	Activity.LoadLayout("land")
	
	'Setting Panel
	Panel3.Left=Panel1.Left
	Panel5.Left=Panel1.Left
	
	'Hide All Panel
	Panel1.Visible=False
	Panel3.Visible=False
	Panel5.Visible=False
	
	

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


'Menu
Sub lvMenu_ItemClick (Position As Int, Value As Object)
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php")
	'lblInfo.Text = "LAST SELECTION: " & Value
	
	If Position=0 Then
		'Hide All Panel First
		Panel3.Visible=False
		Panel5.Visible=False
		'Show Panel
		Panel1.Visible=True
		
	Else If Position=1 Then
		'Hide All Panel First
		Panel1.Visible=False
		Panel5.Visible=False
		
		'Show Panel
		Panel3.Visible=True
			
	Else If Position=2 Then
		'Hide All Panel First
		Panel1.Visible=False
		Panel3.Visible=False
		'Show Panel
		Panel5.Visible=True
				
	End If
	
	PanelWithSidebar.CloseSidebar
End Sub

Sub Menu_onFullyOpen
	Log("FULLY OPEN")
End Sub

Sub Menu_onFullyClosed
	Log("FULLY CLOSED")
End Sub

Sub Menu_onMove(IsOpening As Boolean)
	Log("MOVE IsOpening=" & IsOpening)
End Sub



Sub Button1_Click
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/allland.php")
End Sub

Sub Button2_Click
	Dim owner_id As String
	owner_id=EditText1.Text
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/land_owner.php?owner_id="&owner_id)
	
	
End Sub