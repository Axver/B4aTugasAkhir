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
	
	'Job
	Dim job2 As HttpJob
	Private Label8 As Label
	Private Label7 As Label
	Private Label6 As Label
	Private Label5 As Label
	Private Label2 As Label
	Private Label3 As Label
	Private Label4 As Label
	Private Label1 As Label
	Private Label12 As Label
	Private Label13 As Label
	Private Label14 As Label
	Private Label16 As Label
	Private Label9 As Label
	Private Label10 As Label
	Private Label11 As Label
	Private Label15 As Label
	Private EditText2 As EditText
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
	
	'Color Setting For Label
	Dim cd As ColorDrawable
	cd.Initialize(Colors.Red,5dip)
	Label1.Background=cd
	Label2.Background=cd
	Label3.Background=cd
	Label4.Background=cd
	Label9.Background=cd
	Label10.Background=cd
	Label11.Background=cd
	Label15.Background=cd
	
	
	Label9.Width=Activity.Width/3
	Label10.Width=Activity.Width/3
	Label11.Width=Activity.Width/3
	Label12.Width=Activity.Width/3
	Label13.Width=Activity.Width/3
	Label14.Width=Activity.Width/3
	
	
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
	'Log("FULLY OPEN")
End Sub

Sub Menu_onFullyClosed
	'Log("FULLY CLOSED")
End Sub

Sub Menu_onMove(IsOpening As Boolean)
	'Log("MOVE IsOpening=" & IsOpening)
End Sub



Sub Button1_Click
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/allland.php")
End Sub

Sub Button2_Click
	Dim owner_id As String
	owner_id=EditText1.Text
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/land_owner.php?owner_id="&owner_id)
	
	'Start Job
	job2.Initialize("land_owner", Me)
	job2.PostString(domain&"ta_v2/endpoint/land_owner.php", "owner_id="&owner_id)
	
	
End Sub



'Job

Sub JobDone (Job As HttpJob)
	
	Log("JobName = " & Job.JobName & ", Success = " & Job.Success)
	If Job.Success = True Then
		Select Job.JobName
			Case "land_owner"
				Log(Job.GetString)
				
				'Json Tree
				Dim parser As JSONParser
				parser.Initialize(Job.GetString)
				Dim root As Map = parser.NextObject
				Dim features As List = root.Get("features")
				For Each colfeatures As Map In features
					Dim properties As Map = colfeatures.Get("properties")
					Dim clan_name As String = properties.Get("clan_name")
					Dim nik As String = properties.Get("nik")
					Dim citizen_name As String = properties.Get("citizen_name")
					Dim address As String = properties.Get("address")
					Dim gender As String = properties.Get("gender")
					Dim phone As String = properties.Get("phone")
					Dim status_name As String = properties.Get("status_name")
					'Dim clan_id As String = properties.Get("clan_id")
					Dim born_date As String = properties.Get("born_date")
					
					'Adding To Label
					Label8.Text=nik
					Label7.Text=citizen_name
					Label6.Text=clan_name
					Label5.Text=gender
					
					Label12.Text=phone
					Label13.Text=status_name
					Label14.Text=born_date
					Label16.Text=address
					
					
					
					Label7.Left=Label2.Left
					Label6.Left=Label3.Left
					Label5.Left=Label4.Left
				Next
				Dim Type As String = root.Get("type")
				
			Case "land_tax"
				Log(Job.GetString)
				Dim parser As JSONParser
				parser.Initialize(Job.GetString)
				Dim root As Map = parser.NextObject
				Dim features As List = root.Get("features")
				For Each colfeatures As Map In features
					Dim geometry As Map = colfeatures.Get("geometry")
					Dim coordinates As List = geometry.Get("coordinates")
					For Each colcoordinates As List In coordinates
						For Each colcolcoordinates As List In colcoordinates
							For Each colcolcolcoordinates As List In colcolcoordinates
								For Each colcolcolcolcoordinates As Double In colcolcolcoordinates
								Next
							Next
						Next
					Next
					Dim Type As String = geometry.Get("type")
					Dim Type As String = colfeatures.Get("tyTypepe")
					Dim properties As Map = colfeatures.Get("properties")
					Dim clan_name As String = properties.Get("clan_name")
					Dim owner_name As String = properties.Get("owner_name")
					Dim gender As String = properties.Get("gender")
					Dim land_owner As String = properties.Get("land_owner")
					Dim phone As String = properties.Get("phone")
					Dim tax_number As String = properties.Get("tax_number")
					Dim status_name As String = properties.Get("status_name")
					Dim x As String = properties.Get("x")
					Dim y As String = properties.Get("y")
					Dim land_id As String = properties.Get("land_id")
					Dim born_date As String = properties.Get("born_date")
				Next
				Dim Type As String = root.Get("type")
			
		End Select
	Else
		Log("Error: " & Job.ErrorMessage)
		ToastMessageShow("Error: " & Job.ErrorMessage, True)
	End If
	Job.Release
End Sub

Sub Button3_Click
	Dim tax_number As String
	tax_number=EditText2.Text
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/land_tax_view.php?tax_number="&tax_number)
	
	'Start Job
	job2.Initialize("land_tax", Me)
	job2.PostString(domain&"ta_v2/endpoint/land_tax.php", "tax_number="&tax_number)
	
End Sub