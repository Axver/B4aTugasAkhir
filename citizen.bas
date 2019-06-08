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
	Dim job2 As HttpJob
	Dim EditText1 As EditText
	Dim JSON As JSONParser
	Dim Map1 As Map
	Dim arr() As String

	Private Panel2 As Panel
	Dim domain As String
	domain="http://4e1037e4.ngrok.io/"
	Private Panel3 As Panel
	Dim wvXtender As WebViewXtender

	Private Panel4 As Panel
	Private EditText1 As EditText
	Private Button1 As Button
	Private fakeActionBar As Panel
	Private Label1 As Label
	Private Label2 As Label
	Private Label3 As Label
	Private Label4 As Label
	Private Label5 As Label
	Private Label6 As Label
	Private Panel5 As Panel
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("citizen")
	Panel4.Visible=False
	
	sm.Initialize(Activity, Me, "SlideMenu", 42dip, 180dip)

	sm.AddItem("Citizen", LoadBitmap(File.DirAssets, "user.png"), 1)
	sm.AddItem("Birth Data", LoadBitmap(File.DirAssets, "baby.png"), 2)
	sm.AddItem("Mortality Data", LoadBitmap(File.DirAssets, "funeral.png"), 3)
	sm.AddItem("Family Cards", LoadBitmap(File.DirAssets, "citizen.png"), 4)
	

	Panel2.Width=Activity.Width
	Panel2.Height=Activity.Height/2

	
	Log(domain&"ta_v2/endpoint/view/layers.php")
	Panel3.Height=Activity.Height/2
	Panel3.Width=Activity.Width
	
	

End Sub


Sub tmrLoad_Tick

	
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
		Panel4.Left=Panel2.Left
		Panel4.Visible=True
		Panel4.Width=Panel2.Width
		Panel4.Height=Panel2.Height/2
		
		
		
		
	Else If Item=2 Then
	
	Else If Item=3 Then
	
	Else If Item=4 Then
		
	End If
End Sub





Sub Button1_Click
	
	Dim citizen_id As String
	citizen_id=EditText1.Text
		
	'Send a POST request
	job2.Initialize("Job2", Me)
	job2.PostString(domain&"ta_v2/endpoint/citizen_by_nik.php", "citizen_id=" & citizen_id)
	
End Sub


Sub JobDone (Job As HttpJob)
	Log("JobName = " & Job.JobName & ", Success = " & Job.Success)
	If Job.Success = True Then
		
		Select Job.JobName
			Case "Job2"
				'print the result to the logs
				Log(Job.GetString)
				JSON.Initialize(Job.GetString)
				'Example
				Map1 = JSON.NextObject
				Log(Map1)
				'arr = Map1.Get("results")
				'Log(arr)
				Label1.Text = "Nik:"&Map1.Get("nik")
				Label2.Text = "Status:"&Map1.Get("status_name")
				Label3.Text = "Clan"&Map1.Get("clan_name")
				Label4.Text = "Name:"&Map1.Get("citizen_name")
				Label5.Text = "Phone:"&Map1.Get("phone")
				Label6.Text = "Gender:"&Map1.Get("gender")
			 
				
		End Select
	Else
		Log("Error: " & Job.ErrorMessage)
		ToastMessageShow("Error: " & Job.ErrorMessage, True)
	End If
	Job.Release
End Sub