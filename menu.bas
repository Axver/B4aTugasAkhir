B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Activity
Version=8.3
@EndOfDesignText@
#Region  Activity Attributes 
	#FullScreen: False
	#IncludeTitle: True
#End Region

Sub Process_Globals
	'These global variables will be declared once when the application starts.
	'These variables can be accessed from all modules.

End Sub

Sub Globals
	'These global variables will be redeclared each time the activity is created.
	'These variables can only be accessed from this module.

	Private Panel9 As Panel
	Private Panel11 As Panel
	Private Button1 As Button
	Private Button2 As Button
	Private Button3 As Button
	Private Button4 As Button
End Sub

Sub Activity_Create(FirstTime As Boolean)
	'Do not forget to load the layout file created with the visual designer. For example:
	'Activity.LoadLayout("Layout1")
	Activity.LoadLayout("menu")
	Panel9.Height=Activity.Height
	Panel9.Width=Activity.Width
	Panel11.Top = Panel9.Height - 20dip
	

End Sub

Sub Activity_Resume

End Sub

Sub Activity_Pause (UserClosed As Boolean)

End Sub


Sub Button1_Click
	
	StartActivity("layer")
	
End Sub

Sub Button2_Click
	StartActivity("citizen")
End Sub

Sub Button3_Click
	StartActivity("Building")
End Sub

Sub Button4_Click
	
End Sub