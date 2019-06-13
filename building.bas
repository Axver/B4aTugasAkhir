B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=8.3
@EndOfDesignText@
#Region Module Attributes
	#FullScreen: False
	#IncludeTitle: True
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
	domain="http://d3e0d215.ngrok.io/"
	Private Panel1 As Panel
	Private Button1 As Button
	Private EditText1 As EditText
	Dim job2 As HttpJob
	Dim length As Int
	
	'Sliding Panels
	Dim SD As SlidingPanels
End Sub

Sub Activity_Create(FirstTime As Boolean)
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
	lvMenu.AddSingleLine("Building By No House")
	lvMenu.AddSingleLine("Building By Owner")
	lvMenu.AddSingleLine("Building Status")
	lvMenu.AddSingleLine("Building Condition")
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
	
	
	Activity.LoadLayout("building")
	
	'Load Panel
	Panel1.Width=Activity.Width
	Panel1.Height=Activity.Height/3*2
	
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


'Job

Sub JobDone (Job As HttpJob)
	Log("JobName = " & Job.JobName & ", Success = " & Job.Success)
	If Job.Success = True Then
		Select Job.JobName
			Case "building"
				'print the result to the logs
				Log(Job.GetString)
				
				Dim parser As JSONParser
				parser.Initialize(Job.GetString)
				Dim root As Map = parser.NextObject
				Dim features As List = root.Get("features")
				Log(length)
				
				If length>0 Then
					Do While length>=0
						Log(length)
						SD.Panels(length).Visible=False
		                length=length-1
					Loop
					'SD.Panels(length).Visible=False
				End If
				length=0
				SD.Initialize("SD",300,Activity,Me,False) 'Initialize the Class.
				SD.ModeLittlePanels(15,50%x,50%x,Activity.Height/6*5,20dip,True) 'Creates the mode of SlidingPanels.
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
						length=length+1
					Next
					Dim Type As String = geometry.Get("type")
					Dim Type As String = colfeatures.Get("type")
					Dim properties As Map = colfeatures.Get("properties")
					Dim clan_name As String = properties.Get("clan_name")
					Dim citizen_id As String = properties.Get("citizen_id")
					Dim gender As String = properties.Get("gender")
					Dim phone As String = properties.Get("phone")
					Dim x As String = properties.Get("x")
					Dim name As String = properties.Get("name")
					Dim y As String = properties.Get("y")
					Dim no_house As String = properties.Get("no_house")
					Dim born_date As String = properties.Get("born_date")
					
					
					
					SD.panels(length).Color = Colors.RGB(Rnd(0,256),Rnd(0,256),Rnd(0,256))
					Dim cvs As Canvas
					cvs.Initialize(SD.Panels(length))
					cvs.DrawText("House: "&no_house,SD.panels(length).Width/3,SD.panels(length).Height/3,Typeface.DEFAULT,5%x/Density,Colors.White,"CENTER")
					cvs.DrawText(x,SD.panels(length).Width/3*2,SD.panels(length).Height/3+40,Typeface.DEFAULT,3%x/Density,Colors.White,"CENTER")
					cvs.DrawText(y,SD.panels(length).Width/3*2,SD.panels(length).Height/3*2+10,Typeface.DEFAULT,3%x/Density,Colors.White,"CENTER")
					'SD.Panels(length).Visible=False
					Log(no_house&x&y)
				Next
				Dim Type As String = root.Get("type")
				
				'SD.Initialize("SD",300,Activity,Me,False) 'Initialize the Class.
				'SD.ModeLittlePanels(15,50%x,50%x,Activity.Height/6*5,20dip,True) 'Creates the mode of SlidingPanels.
				'---Add elements to Panels---
				'Dim c As Int
				'For c = 0 To length-1
				'	SD.panels(c).Color = Colors.RGB(Rnd(0,256),Rnd(0,256),Rnd(0,256))
				'	Dim cvs As Canvas
				'	cvs.Initialize(SD.Panels(c))
				'	cvs.DrawText("Panel #"&c,SD.panels(c).Width/2,SD.panels(c).Height/2,Typeface.DEFAULT,9%x/Density,Colors.White,"CENTER")
				'Next
				SD.Start(0) 'Start the SlidingPanels.
				
				
			
		End Select
	Else
		Log("Error: " & Job.ErrorMessage)
		ToastMessageShow("Error: " & Job.ErrorMessage, True)
	End If
	Job.Release
End Sub


Sub Button1_Click
	Dim citizen_id As String
	citizen_id=EditText1.Text
	
	If citizen_id=Null Then
		citizen_id=""
	End If
	
	job2.Initialize("building", Me)
	job2.PostString(domain&"ta_v2/endpoint/building_bo.php", "citizen_id="&citizen_id)
	
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/building_bo.php?citizen_id="&citizen_id)
	
End Sub