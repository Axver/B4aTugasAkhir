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
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.
	Dim sm As SlideMenu

	Private WebView1 As WebView
	Private Panel2 As Panel
	Dim domain As String
	domain="http://334473e2.ngrok.io/"
	Private Panel3 As Panel
	Dim wvXtender As WebViewXtender
	Private Label1 As Label
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("layer")
	sm.Initialize(Activity, Me, "SlideMenu", 42dip, 180dip)

	sm.AddItem("All Items", LoadBitmap(File.DirAssets, "bomb.png"), 1)
	sm.AddItem("Block A", LoadBitmap(File.DirAssets, "book_add.png"), 2)
	sm.AddItem("Block B", LoadBitmap(File.DirAssets, "book_add.png"), 3)
	sm.AddItem("Block C", LoadBitmap(File.DirAssets, "book_add.png"), 4)
	sm.AddItem("Block D", LoadBitmap(File.DirAssets, "book_add.png"), 5)
	sm.AddItem("Block E", LoadBitmap(File.DirAssets, "book_add.png"), 6)
	sm.AddItem("Block F", LoadBitmap(File.DirAssets, "book_add.png"), 7)
	sm.AddItem("Block G", LoadBitmap(File.DirAssets, "book_add.png"), 8)
	sm.AddItem("Block H", LoadBitmap(File.DirAssets, "book_add.png"), 9)
	WebView1.Width=Activity.Width
	WebView1.Height=Activity.Height
	Panel2.Width=WebView1.Width
	Panel2.Height=WebView1.Height
	tmrLoad.Initialize("tmrLoad", 200) ' 1000 = 1 second
	WebView1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php")
	tmrLoad.Enabled = True
	
	Log(domain&"ta_v2/endpoint/view/layers.php")
	Panel3.Height=Activity.Height/2
	Panel3.Width=Activity.Width
	
	

End Sub


Sub tmrLoad_Tick
	'Handle tick events
	DoEvents
	Label1.Text = "Loading "&wvXtender.getProgress(WebView1) & "%"
	DoEvents
	If Label1.Text = "Loading 100%" Then
		tmrLoad.Enabled = False
		Label1.Text=""
		
	End If
	
End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub

'We capture the menu and back keys
Sub Activity_KeyPress (KeyCode As Int) As Boolean
	'Pressing the back key while the slidemenu is open will close it
	If KeyCode = KeyCodes.KEYCODE_BACK And sm.isVisible Then
		sm.Hide
		Return True
	End If

	'Pressing the menu key will open/close the slidemenu
	If KeyCode = KeyCodes.KEYCODE_MENU Then
		If sm.isVisible Then sm.Hide Else sm.Show
		Return True
	End If
End Sub

'Show the slidemenu
Sub btnShow_Click
	sm.Show
End Sub

'Event sub which is called when an item in the slidemenu is clicked
Sub SlideMenu_Click(Item As Object)
	ToastMessageShow("Item clicked: " & Item, False)
	If Item=1 Then
		tmrLoad.Initialize("tmrLoad", 200) ' 1000 = 1 second
		WebView1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=all")
		tmrLoad.Enabled = True
	Else If Item=2 Then
		tmrLoad.Initialize("tmrLoad", 200) ' 1000 = 1 second
	    WebView1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=A")
		tmrLoad.Enabled = True
	Else If Item=3 Then
		tmrLoad.Initialize("tmrLoad", 200) ' 1000 = 1 second
		WebView1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=B")
		tmrLoad.Enabled = True
	Else If Item=4 Then
		tmrLoad.Initialize("tmrLoad", 200) ' 1000 = 1 second
		WebView1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=C")
		tmrLoad.Enabled = True
	Else If Item=5 Then
		tmrLoad.Initialize("tmrLoad", 200) ' 1000 = 1 second
		WebView1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=D")
		tmrLoad.Enabled = True
	Else If Item=6 Then
		tmrLoad.Initialize("tmrLoad", 200) ' 1000 = 1 second
		WebView1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=E")
		tmrLoad.Enabled = True
	Else If Item=7 Then
		tmrLoad.Initialize("tmrLoad", 200) ' 1000 = 1 second
		WebView1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=F")
		tmrLoad.Enabled = True
	Else If Item=8 Then
		tmrLoad.Initialize("tmrLoad", 200) ' 1000 = 1 second
		WebView1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=G")
		tmrLoad.Enabled = True
	Else If Item=9 Then
		tmrLoad.Initialize("tmrLoad", 200) ' 1000 = 1 second
		WebView1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php?request=H")
		tmrLoad.Enabled = True
	End If
End Sub



