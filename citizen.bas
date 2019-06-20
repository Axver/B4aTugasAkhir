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
	domain="http://f7bedc8d.ngrok.io/"
	Dim i As Int
	
	Dim job2 As HttpJob
	Dim JSON As JSONParser
	Dim Map1 As Map
	Dim arr() As String
	Dim parser As JSONParser

	Private EditText1 As EditText
	Private ListView1 As ListView
	Private ListView2 As ListView
	Private Button1 As Button
	Private Label1 As Label
	Private Panel3 As Panel
	Private Panel6 As Panel
	Private Panel4 As Panel
	Private Panel5 As Panel
	Private Panel1 As Panel
	Private Panel8 As Panel
	Private Panel7 As Panel
	Private Panel9 As Panel
	Private EditText2 As EditText
	
	
	'Table ScrollView
	
	'Birth Data
	
	
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
	lvMenu.AddSingleLine("Search Citizen")
	lvMenu.AddSingleLine("Family Card")
	lvMenu.AddSingleLine("Birth Data")
	lvMenu.AddSingleLine("Mortality Data")
	lvMenu.AddSingleLine("Outcomes")
	lvMenu.SingleLineLayout.Label.TextColor = Colors.Black
	lvMenu.Color = Colors.Transparent
	lvMenu.ScrollingBackgroundColor = Colors.Transparent
	PanelWithSidebar.Sidebar.AddView(lvMenu, 20dip, 25dip, -1, -1)

	Webview1.Initialize("")
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php")
	'lblInfo.Text = "Click the button to open/close the sliding menu."
	'lblInfo.TextColor = Colors.Black
	'lblInfo.TextSize = 24
	PanelWithSidebar.ContentPanel.AddView(Webview1, 0dip, 0dip, 100%x - 0dip, 100%y - 1dip)

	btnMenu.Initialize("")
	btnMenu.SetBackgroundImage(LoadBitmap(File.DirAssets, "menu.png"))
	FakeActionBar.AddView(btnMenu, 100%x - BarSize, 0, BarSize, BarSize)
	PanelWithSidebar.SetOpenCloseButton(btnMenu)
	Activity.LoadLayout("citizen")
	
	Panel1.Width=Activity.Width
	
	'Hidden Component First
	EditText1.Visible=False
	Button1.Visible=False
	Label1.Visible=False
	ListView1.Visible=False
	'Family Card
	'Panel3.Visible=False
	'Panel6.Visible=False
	Panel8.Visible=False
	Panel3.Visible=False
	

	
	
	
	
	
End Sub

Sub Activity_Resume
End Sub

Sub Activity_Pause (UserClosed As Boolean)
End Sub

Sub lvMenu_ItemClick (Position As Int, Value As Object)
	'lblInfo.Text = "LAST SELECTION: " & Value
	Log(Position)
	Log(Value)
	If Position=0 Then
		'Hidden All Component First
		Panel8.Visible=False
		Panel3.Visible=False
		
		'Show Element
		Panel1.Visible=True
		EditText1.Visible=True
		Button1.Visible=True
		Label1.Visible=True
		ListView1.Visible=True
		
		
	
	Else If Position=1 Then
		'Hidden All Component First
		EditText1.Visible=False
		Button1.Visible=False
		Label1.Visible=False
		ListView1.Visible=False
		Panel3.Visible=False
		
		'Show Panel
		Panel1.Visible=False
		Panel8.Left=Panel1.Left
	
		Panel8.Visible=True
		
	Else If Position=2 Then
		'Hidden ALl Componen First
		Panel1.Visible=False
		Panel8.Visible=False
		
		'Show Panel
		Panel3.Visible=True
		Panel3.Left=Panel1.Left
		Panel3.Top=Panel1.Top
	
		
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
				
				Case "family_card"
				If Job.GetString="[]" Then
					
					
				Else
					JSON.Initialize(Job.GetString)
					Map1 = JSON.NextObject
					Log(Map1)
					'arr = Map1.Get("results")
					'Log(arr)
					parser.Initialize(Job.GetString)
					Dim root As Map = parser.NextObject
					Dim Type As String = root.Get("type")
					Dim results As List = root.Get("results")
					
					Dim H As Int
					H=0
					Dim gd1 As GradientDrawable
					gd1.Initialize("BOTTOM_TOP", Array As Int (Colors.Magenta, Colors.Magenta, Colors.Magenta))
					
					Dim gd2 As GradientDrawable
					gd2.Initialize("BOTTOM_TOP", Array As Int (Colors.Gray, Colors.Gray, Colors.Gray))
					
					Dim lv_size As Int
					lv_size=ListView2.Size
					lv_size=lv_size-1
					Log(lv_size)
					If lv_size>0 Then
						
					Do While lv_size>=0
						
						Log(lv_size)
						ListView2.RemoveAt(lv_size)
						lv_size=lv_size-1
						
					Loop
					
					'Still Error
						
						'ListView2.RemoveAt(0)
					End If
					
					
					
					For Each colresults As Map In results
						H=H+1
						Dim clan_name As String = colresults.Get("clan_name")
						Dim citizen_name As String = colresults.Get("citizen_name")
						'Log(citizen_name)
						'Dim address As String = colresults.Get("address")
						Dim gender As String = colresults.Get("gender")
						'Dim family_no As String = colresults.Get("family_no")
						'Dim created_at As String = colresults.Get("created_at")
						'Dim photos As String = colresults.Get("photos")
						Dim nik As String = colresults.Get("nik")
						'Dim family_card_id As String = colresults.Get("family_card_id")
						'Dim updated_at As String = colresults.Get("updated_at")
						Dim phone As String = colresults.Get("phone")
						Dim born_date As String = colresults.Get("born_date")
						Dim status As String = colresults.Get("status")
						ListView2.SingleLineLayout.Label.TextColor = Colors.Black
						If H Mod 2=0 Then
							ListView2.SingleLineLayout.Background = gd1
							Else
							ListView2.SingleLineLayout.Background = gd2
						End If
					
						ListView2.AddSingleLine("Clan: "&clan_name&" "&" Name: "&citizen_name&" Gender: "&gender&" "&" Id: "&nik&" "&" Status: "&status&" "&" Born Date: "&born_date&" "&" Phone: "&phone&" ")
						ListView2.SingleLineLayout.Label.TextSize = 12
					Next
				
				End If
				
		End Select
	Else
		Log("Error: " & Job.ErrorMessage)
		ToastMessageShow("Error: " & Job.ErrorMessage, True)
	End If
	Job.Release
End Sub

Sub Button2_Click
	
	
	Dim family_no As String
	family_no=EditText2.Text
	
	If family_no=Null Then
		family_no=""
	End If
		
	'Send a POST request
	job2.Initialize("family_card", Me)
	job2.PostString(domain&"ta_v2/endpoint/family_card.php", "family_no=" & family_no)
	Webview1.LoadUrl(domain&"ta_v2/endpoint/view/layers.php")
	
	
End Sub