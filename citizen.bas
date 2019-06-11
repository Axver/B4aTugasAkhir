B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=8.3
@EndOfDesignText@
#Region Module Attributes
	#FullScreen: False
	#IncludeTitle: False
#End Region

'Activity module
Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.
End Sub

Sub Globals
	Dim FakeActionBar, UnderActionBar As Panel
	Dim PanelWithSidebar As ClsSlidingSidebar
	Dim btnMenu As Button
	Dim lvMenu As ListView
	Dim lblInfo As Label
	Dim Webview1 As WebView
	Dim domain As String
	domain="http://e7ada07d.ngrok.io/"
	
	Dim job2 As HttpJob
	Dim JSON As JSONParser
	Dim Map1 As Map
	Dim arr() As String

	Private EditText1 As EditText
	Private ListView1 As ListView
	Private ListView2 As ListView
End Sub

Sub Activity_Create(FirstTime As Boolean)
	Activity.LoadLayout("citizen")
	Dim BarSize As Int: BarSize = 60dip
	FakeActionBar.Initialize("")
	FakeActionBar.Color = Colors.RGB(20, 20, 100) 'Dark blue
	Activity.AddView(FakeActionBar, 0, 0, 100%x, BarSize)
	
	Dim LightCyan As Int: LightCyan = Colors.RGB(0, 95, 170)
	UnderActionBar.Initialize("")
	UnderActionBar.Color = LightCyan
	Activity.AddView(UnderActionBar, 0, BarSize, 100%x, 100%y - BarSize)
	
	PanelWithSidebar.Initialize(UnderActionBar, 190dip, 2, 1, 500, 500)
	PanelWithSidebar.ContentPanel.Color = LightCyan
	PanelWithSidebar.Sidebar.Background = PanelWithSidebar.LoadDrawable("popup_inline_error")
	PanelWithSidebar.SetOnChangeListeners(Me, "Menu_onFullyOpen", "Menu_onFullyClosed", "Menu_onMove")

	lvMenu.Initialize("lvMenu")
	lvMenu.AddSingleLine("1st option")
	lvMenu.AddSingleLine("2nd option")
	lvMenu.AddSingleLine("3rd option")
	lvMenu.SingleLineLayout.Label.TextColor = Colors.Black
	lvMenu.Color = Colors.Transparent
	lvMenu.ScrollingBackgroundColor = Colors.Transparent
	PanelWithSidebar.Sidebar.AddView(lvMenu, 20dip, 25dip, -1, -1)

	Webview1.Initialize("")
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php")
	'lblInfo.Text = "Click the button to open/close the sliding menu."
	'lblInfo.TextColor = Colors.Black
	'lblInfo.TextSize = 24
	PanelWithSidebar.ContentPanel.AddView(Webview1, 0dip, 0dip, 100%x - 0dip, 100%y - 0dip)

	btnMenu.Initialize("")
	btnMenu.SetBackgroundImage(LoadBitmap(File.DirAssets, "menu.png"))
	FakeActionBar.AddView(btnMenu, 100%x - BarSize, 0, BarSize, BarSize)
	PanelWithSidebar.SetOpenCloseButton(btnMenu)
	
	Activity.LoadLayout("citizen")
End Sub

Sub Activity_Resume
End Sub

Sub Activity_Pause (UserClosed As Boolean)
End Sub

Sub lvMenu_ItemClick (Position As Int, Value As Object)
	'lblInfo.Text = "LAST SELECTION: " & Value
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
	
	Dim citizen_id As String
	citizen_id=EditText1.Text
	
	If citizen_id=Null Then
		citizen_id=""
	End If
		
	'Send a POST request
	job2.Initialize("Job2", Me)
	job2.PostString(domain&"ta_v2/endpoint/citizen_by_nik.php", "citizen_id=" & citizen_id)
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/land_owning.php?owner_id="&citizen_id)
	
End Sub


Sub JobDone (Job As HttpJob)
	Log("JobName = " & Job.JobName & ", Success = " & Job.Success)
	If Job.Success = True Then
		
		Select Job.JobName
			Case "Job2"
				'print the result to the logs
				Log(Job.GetString)
				If Job.GetString="[]" Then
					
				Else
					JSON.Initialize(Job.GetString)
					'Example
					Map1 = JSON.NextObject
					Log(Map1)
					'arr = Map1.Get("results")
					'Log(arr)
					'Label1.Text = "Nik:"&Map1.Get("nik")
					'Label2.Text = "Status:"&Map1.Get("status_name")
					'Label3.Text = "Clan:"&Map1.Get("clan_name")
					'Label4.Text = "Name:"&Map1.Get("citizen_name")
					'Label5.Text = "Phone:"&Map1.Get("phone")
					'Label6.Text = "Gender:"&Map1.Get("gender")
				
					ListView1.SingleLineLayout.Label.TextColor = Colors.Black
				
					ListView1.AddSingleLine( "Nik:"&Map1.Get("nik"))
					ListView1.AddSingleLine( "Status:"&Map1.Get("status_name"))
					ListView1.AddSingleLine( "Clan:"&Map1.Get("clan_name"))
					ListView1.AddSingleLine( "Name:"&Map1.Get("citizen_name"))
					ListView1.AddSingleLine( "Phone:"&Map1.Get("phone"))
					ListView1.AddSingleLine( "Gender:"&Map1.Get("gender"))
					ListView1.SingleLineLayout.Label.TextSize = 14
			 
				End If
				
				
		End Select
	Else
		Log("Error: " & Job.ErrorMessage)
		ToastMessageShow("Error: " & Job.ErrorMessage, True)
	End If
	Job.Release
End Sub