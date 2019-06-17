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
	Dim id1 As String
	Dim id2 As String
	id1="0"
	id2="0"
	Dim FakeActionBar, UnderActionBar As Panel
	Dim PanelWithSidebar As ClsSlidingSidebar
	Dim btnMenu As Button
	Dim lvMenu As ListView
	Dim lblInfo As Label
	Dim Webview1 As WebView
	Dim domain As String
	domain="http://7a880193.ngrok.io/"
	Private Panel1 As Panel
	Private Button1 As Button
	Private EditText1 As EditText
	Dim job2 As HttpJob
	Dim length As Int
	
	'Sliding Panels
	Dim SD As SlidingPanels
	Private Panel3 As Panel
	Private Panel5 As Panel
	Private Spinner1 As Spinner
	Private Spinner2 As Spinner
	Private Button3 As Button
	
	Dim Spinner1map As Map
	Dim Spinner2map As Map
	
	Private EditText2 As EditText
	Private ListView1 As ListView
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
	lvMenu.AddSingleLine("Building By Occupants")
	lvMenu.AddSingleLine("Building By No House")
	lvMenu.AddSingleLine("Status And Condition")
	
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
	
	'Here it is
	Panel1.Visible=False
	Panel3.Visible=False
	Panel5.Visible=False
	
	'Setting Panels
	Panel3.Height=Panel1.Height
	Panel3.Width=Panel1.Width
	Panel3.Left=Panel1.Left
	Panel5.Width=Panel1.Width
	Panel5.Left=Panel1.Left
	
	
	
	
	'Spinner1 Setting
	Spinner1map.Initialize
	Spinner2map.Initialize
	Spinner1.DropdownBackgroundColor=Colors.White
	Spinner1.Add("All")
	Spinner1map.Put("All","0")
	Spinner1.Add("Non Active")
	Spinner1map.Put("Non Active","1")
	Spinner1.Add("Traditional Heritage")
	Spinner1map.Put("Traditional Heritage","2")
	Spinner1.Add("Civil Heritage")
	Spinner1map.Put("Civil Heritage","3")
	Spinner1.Add("Islamic Heritage")
	Spinner1map.Put("Islamic Heritage","4")
	Spinner1.Add("Private Property")
	Spinner1map.Put("Private Property","5")
	
	
	'Spinnner2 Setting
	Spinner2.DropdownBackgroundColor=Colors.White
	Spinner2.Add("All")
	Spinner2map.Put("All","0")
	Spinner2.Add("Semi Permanent")
	Spinner2map.Put("Semi Permanent","1")
	Spinner2.Add("Permanent")
	Spinner2map.Put("Permanent","2")
	
	'Listview1 Setting
	ListView1.SingleLineLayout.Label.TextColor = Colors.Black
	ListView1.SingleLineLayout.Label.TextSize = 14
	
	
End Sub

Sub Activity_Resume
End Sub

Sub Activity_Pause (UserClosed As Boolean)
End Sub

Sub lvMenu_ItemClick (Position As Int, Value As Object)
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php")
	'lblInfo.Text = "LAST SELECTION: " & Value
	If Position=0 Then
		'Hide All Panel First
		Panel3.Visible=False
		Panel5.Visible=False
		
		'Show It's selft panel
		Panel1.Visible=True
	Else If Position=1 Then
		'Hide All Panel First
		Panel1.Visible=False
		Panel5.Visible=False
		'SHow Its own Panel
		Panel3.Visible=True
		
		
	Else If Position=2 Then
		'Hide ALl Panel First
		Panel3.Visible=False
		Panel1.Visible=False
		'Show Its own Panel
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
					cvs.DrawText(y,SD.panels(length).Width/3*2,SD.panels(length).Height/3+10,Typeface.DEFAULT,3%x/Density,Colors.White,"CENTER")
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
				
			Case "nohouse"
				'JSON Parser For Search By No House Job
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
					
					Dim lv_size1 As Int
					lv_size1=ListView1.Size
					lv_size1=lv_size1-1
					Log(lv_size1)
					If lv_size1>0 Then
						
						Do While lv_size1>=0
						
							Log(lv_size1)
							ListView1.RemoveAt(lv_size1)
							lv_size1=lv_size1-1
						
						Loop
					
						'Still Error
						
						'ListView2.RemoveAt(0)
					End If
					
					ListView1.AddSingleLine( "Name: "&name&" "&"Citizen Id: "&citizen_id&" "&"Gender: "&gender&" "&"Phone: "&phone&" "&"Clan Name: "&clan_name&"Born Date: "&born_date)
					
				Next
				Dim Type As String = root.Get("type")
				Log(Job.GetString)
			
		End Select
	Else
		Log("Error: " & Job.ErrorMessage)
		ToastMessageShow("Error: " & Job.ErrorMessage, True)
	End If
	Job.Release
End Sub


Sub Button1_Click
	'Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php")
	Dim citizen_id As String
	citizen_id=EditText1.Text
	
	If citizen_id=Null Then
		citizen_id=""
	End If
	
	job2.Initialize("building", Me)
	job2.PostString(domain&"ta_v2/endpoint/building_bo.php", "citizen_id="&citizen_id)
	
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/building_bo.php?citizen_id="&citizen_id)
	
End Sub


'Panel Click

Sub SD_Click (TouchData As TouchData)
	'Is generated with the Click event.
	ToastMessageShow("Clicked on Panel: "&TouchData.Tag&" / X: "&TouchData.X&" / Y: "&TouchData.Y,False)
End Sub

Sub Button3_Click
	'Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php")
	
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/condition_status.php?status="&id1&"&condition="&id2)
'Show Selected Spinner Value
	Log(id1)
	Log(id2)
	
	
End Sub

Sub Spinner1_ItemClick (Position As Int, Value As Object)
	id1 = Spinner1map.Get(Value)
	
'Log(id1)
'Log(id2)
End Sub


Sub Spinner2_ItemClick (Position As Int, Value As Object)
	
	id2 = Spinner2map.Get(Value)
'Log(id1)
'Log(id2)
End Sub

Sub Button2_Click
	'Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php")
	Dim nohouse As String
	nohouse=EditText2.Text
	
	job2.Initialize("nohouse", Me)
	job2.PostString(domain&"ta_v2/endpoint/building_no.php", "nohouse="&nohouse)
	
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/building_nov.php?nohouse="&nohouse)
	
	
	
End Sub