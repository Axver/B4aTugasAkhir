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
	Dim tmrLoad As Timer
End Sub

Sub Globals
	Dim Webview1 As WebView
	Dim domain As String
	domain="http://0ab75d92.ngrok.io/"
	Dim FakeActionBar, UnderActionBar As Panel
	Dim PanelWithSidebar As ClsSlidingSidebar
	Dim btnMenu As Button
	Dim lvMenu As ListView

	Private Panel1 As Panel
	Private Button1 As Button
	Private Panel3 As Panel
	Private Panel5 As Panel
	Private EditText1 As EditText
	
	'Slide Menu 2
	Dim sm As SlideMenu
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
	lvMenu.AddSingleLine("ALL")
	lvMenu.AddSingleLine("Block A")
	lvMenu.AddSingleLine("Block B")
	lvMenu.AddSingleLine("Block C")
	lvMenu.AddSingleLine("Block D")
	lvMenu.AddSingleLine("Block E")
	lvMenu.AddSingleLine("Block F")
	lvMenu.AddSingleLine("Block G")
	lvMenu.AddSingleLine("Block H")
	
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
	
	
	
	Activity.LoadLayout("layer")
	'Panel1.Height=Activity.Height-Webview1.Height
	

End Sub



Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

'Menu Here
'Menu
Sub lvMenu_ItemClick (Position As Int, Value As Object)
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php")
	'lblInfo.Text = "LAST SELECTION: " & Value
	
	If Position=0 Then
		
		Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=all")
	Else If Position=1 Then
		Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=A")		
	Else If Position=2 Then
		Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=B")
	Else If Position=3 Then
		Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=C")
	Else If Position=4 Then
		Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=D")
	Else If Position=5 Then
		Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=E")
	Else If Position=6 Then
		Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=F")
	Else If Position=7 Then
		Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=G")
	Else If Position=8 Then
		Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=H")			
	End If
	
	PanelWithSidebar.CloseSidebar
End Sub

Sub Menu_onFullyOpen
	'Log("FULLY OPEN")
End Sub

Sub Menu_onFullyClosed
	'Log("FULLY CLOSED")
End Sub

Sub Menu_onMove(IsOpening As Boolean)
	'Log("MOVE IsOpening=" & IsOpening)
End Sub