B4A=true
Group=Default Group
ModulesStructureVersion=1
Type=Class
Version=8.3
@EndOfDesignText@
'Class module
'Version: 1.1
'Author: Frédéric Leneuf-Magaud
'This class is released as donationware.
Sub Class_Globals
	Private pnlContent As Panel
	Private pnlSidebar As Panel
	Private pnlGesture As Panel
	Private sbParent As Panel
	Private sbPosition As Byte
	Private sbAnimType As Byte
	Private sbInterpolator As Float
	Private sbAnimInProgress As Byte
	Private sbStopAnim As Boolean
	Private sbOpenDuration As Int
	Private sbCloseDuration As Int
	Private sbIsVisible As Boolean
	Private sbIsOpening As Boolean
	Private sbStartX, sbStartY As Int
	Private pnlSidebarStartX, pnlSidebarStartY As Int
	Private pnlContentStartX, pnlContentStartY As Int
	Private sbHandle As View
	Private sbFinalMovement As Byte
	Private sbSubFullyOpen As String
	Private sbSubFullyClosed As String
	Private sbSubMove As String
	Private sbModule As Object
	Private FROM_OPEN As Boolean:  FROM_OPEN = True
	Private FROM_CLOSE As Boolean: FROM_CLOSE = False
	Private OPEN_ANIM As Boolean:  OPEN_ANIM = False
	Private CLOSE_ANIM As Boolean: CLOSE_ANIM = True
	Private OPENING As Byte: OPENING = 1
	Private CLOSING As Byte: CLOSING = 2
End Sub

'Initializes the content panel and its sidebar
'<B>Parent</B> = activity or panel holding the content panel and its sidebar
'<B>SidebarSize</B> = width or height of the sidebar
'<B>SidebarPosition</B>:<I>
' 0 = left
' 1 = right
' 2 = top
' 3 = bottom</I>
'<B>AnimType</B>:<I>
' 0 = the content panel is pushed to the side and reveals the sidebar
' 1 = the content panel and the sidebar move together to the side
' 2 = the sidebar slides above the content panel</I>
'<B>Open</B>/<B>CloseDuration</B> = duration of the opening/closing animation
Public Sub Initialize(Parent As Panel, SidebarSize As Int, SidebarPosition As Byte, AnimType As Byte, OpenDuration As Int, CloseDuration As Int)
	sbParent = Parent
	pnlContent.Initialize("Block")
	Parent.AddView(pnlContent, 0, 0, getParentWidth, getParentHeight)

	pnlSidebar.Initialize("Block")
	Select SidebarPosition
		Case 0 'LEFT
			If AnimType = 0 Then
				Parent.AddView(pnlSidebar, 0, 0, SidebarSize, getParentHeight)
			Else
				Parent.AddView(pnlSidebar, -SidebarSize, 0, SidebarSize, getParentHeight)
			End If
		Case 1 'RIGHT
			If AnimType = 0 Then
				Parent.AddView(pnlSidebar, getParentWidth - SidebarSize, 0, SidebarSize, getParentHeight)
			Else
				Parent.AddView(pnlSidebar, getParentWidth, 0, SidebarSize, getParentHeight)
			End If
		Case 2 'TOP
			If AnimType = 0 Then
				Parent.AddView(pnlSidebar, 0, 0, getParentWidth, SidebarSize)
			Else
				Parent.AddView(pnlSidebar, 0, -SidebarSize, getParentWidth, SidebarSize)
			End If
		Case 3 'BOTTOM
			If AnimType = 0 Then
				Parent.AddView(pnlSidebar, 0, getParentHeight - SidebarSize, getParentWidth, SidebarSize)
			Else
				Parent.AddView(pnlSidebar, 0, getParentHeight, getParentWidth, SidebarSize)
			End If
	End Select
	If AnimType = 0 Then pnlSidebar.SendToBack

	sbPosition = SidebarPosition
	sbAnimType = AnimType
	sbInterpolator = 0.7
	sbOpenDuration = OpenDuration
	sbCloseDuration = CloseDuration
	sbIsVisible = False
End Sub

Private Sub Block_Touch(Action As Int, X As Float, Y As Float)
	' This sub is just there to prevent the Touch events from passing through
End Sub

'Gets a drawable from the Android system resources
Public Sub LoadDrawable(Name As String) As Object
	Dim r As Reflector
	r.Target = r.GetContext
	r.Target = r.RunMethod("getResources")
	r.Target = r.RunMethod("getSystem")
	Dim ID_Drawable As Int
	ID_Drawable = r.RunMethod4("getIdentifier", Array As Object(Name, "drawable", "android"), _
	                                            Array As String("java.lang.String", "java.lang.String", "java.lang.String"))
	r.Target = r.GetContext
	r.Target = r.RunMethod("getResources")
	Return r.RunMethod2("getDrawable", ID_Drawable, "java.lang.int")
End Sub

'Sets the three event handlers
'<B>Module</B> = Me
'<B>SubOnFullyOpen</B> = name of the sub handling the onFullyOpen event
'  Handler: <I>Sub onFullyOpen</I>
'<B>SubOnFullyClosed</B> = name of the sub handling the onFullyClosed event
'  Handler: <I>Sub onFullyClosed</I>
'<B>SubOnMove</B> = name of the sub handling the onMove event
'  Handler: <I>Sub onMove(IsOpening As Boolean)</I>
Public Sub SetOnChangeListeners(Module As Object, SubOnFullyOpen As String, SubOnFullyClosed As String, SubOnMove As String)
	sbModule = Module
	sbSubFullyOpen = SubOnFullyOpen
	sbSubFullyClosed = SubOnFullyClosed
	sbSubMove = SubOnMove
End Sub

Public Sub Sidebar As Panel
	Return pnlSidebar
End Sub

Public Sub ContentPanel As Panel
	Return pnlContent
End Sub

#Region Parent Width/Height
'Gets the real width of the parent
'In some containers like TabHosts, the width property returns -1, so this function uses a different method to get width
Private Sub getParentWidth As Int
	If sbParent.Width < 0 Then
		Dim r As Reflector, RealWidth As Int
		r.Target = sbParent
		RealWidth = r.RunMethod("getWidth")
		If RealWidth = 0 Then
			DoEvents
			RealWidth = r.RunMethod("getWidth")
		End If
		Return RealWidth
	Else
		Return sbParent.Width
	End If
End Sub

'Gets the real height of the parent
'In some containers like TabHosts, the height property returns -1, so this function uses a different method to get height
Private Sub getParentHeight As Int
	If sbParent.Height < 0 Then
		Dim r As Reflector, RealHeight As Int
		r.Target = sbParent
		RealHeight = r.RunMethod("getHeight")
		If RealHeight = 0 Then
			DoEvents
			RealHeight = r.RunMethod("getHeight")
		End If
		Return RealHeight
	Else
		Return sbParent.Height
	End If
End Sub
#End Region

#Region Animation
'Sets the animation rate of change
'<B>Value</B>:
'&lt; 1 = decelerate
'1 = linear
'&gt; 1 = accelerate
Public Sub SetInterpolator(Value As Float)
	sbInterpolator = Value
End Sub

Private Sub Animate(Progression As Int)
	Select sbPosition
		Case 0 'LEFT
			If sbAnimType > 0 Then pnlSidebar.Left = pnlSidebarStartX + Progression
			If sbAnimType < 2 Then pnlContent.Left = pnlContentStartX + Progression
			If sbAnimType = 2 Then
				If sbHandle.IsInitialized Then sbHandle.Left = pnlSidebar.Left + pnlSidebar.Width
				If pnlGesture.IsInitialized Then pnlGesture.Left = pnlSidebar.Left + pnlSidebar.Width - (pnlGesture.Width / 2)
			Else
				If sbHandle.IsInitialized Then sbHandle.Left = pnlContent.Left
				If pnlGesture.IsInitialized Then pnlGesture.Left = pnlContent.Left - (pnlGesture.Width / 2)
			End If
		Case 1 'RIGHT
			If sbAnimType > 0 Then pnlSidebar.Left = pnlSidebarStartX + Progression
			If sbAnimType < 2 Then pnlContent.Left = pnlContentStartX + Progression
			If sbAnimType = 2 Then
				If sbHandle.IsInitialized Then sbHandle.Left = pnlSidebar.Left - sbHandle.Width
				If pnlGesture.IsInitialized Then pnlGesture.Left = pnlSidebar.Left - (pnlGesture.Width / 2)
			Else
				If sbHandle.IsInitialized Then sbHandle.Left = pnlContent.Left + pnlContent.Width - sbHandle.Width
				If pnlGesture.IsInitialized Then pnlGesture.Left = pnlContent.Left + pnlContent.Width - (pnlGesture.Width / 2)
			End If
		Case 2 'TOP
			If sbAnimType > 0 Then pnlSidebar.Top = pnlSidebarStartY + Progression
			If sbAnimType < 2 Then pnlContent.Top = pnlContentStartY + Progression
			If sbAnimType = 2 Then
				If sbHandle.IsInitialized Then sbHandle.Top = pnlSidebar.Top + pnlSidebar.Height
				If pnlGesture.IsInitialized Then pnlGesture.Top = pnlSidebar.Top + pnlSidebar.Height - (pnlGesture.Height / 2)
			Else
				If sbHandle.IsInitialized Then sbHandle.Top = pnlContent.Top
				If pnlGesture.IsInitialized Then pnlGesture.Top = pnlContent.Top - (pnlGesture.Height / 2)
			End If
		Case 3 'BOTTOM
			If sbAnimType > 0 Then pnlSidebar.Top = pnlSidebarStartY + Progression
			If sbAnimType < 2 Then pnlContent.Top = pnlContentStartY + Progression
			If sbAnimType = 2 Then
				If sbHandle.IsInitialized Then sbHandle.Top = pnlSidebar.Top - sbHandle.Height
				If pnlGesture.IsInitialized Then pnlGesture.Top = pnlSidebar.Top - (pnlGesture.Height / 2)
			Else
				If sbHandle.IsInitialized Then sbHandle.Top = pnlContent.Top + pnlContent.Height - sbHandle.Height
				If pnlGesture.IsInitialized Then pnlGesture.Top = pnlContent.Top + pnlContent.Height - (pnlGesture.Height / 2)
			End If
	End Select
	sbIsVisible = (CalcDistance(FROM_CLOSE) <> 0)
End Sub

Private Sub AnimateSidebar(Close As Boolean)
	' Calculates the animation distance and duration (the closer it is to the position to reach, the shorter the duration)
	Dim AnimDistance, AnimDuration As Int
	Dim PctStillToMove As Float
	If Close Then
		AnimDistance = CalcDistance(FROM_CLOSE)
		PctStillToMove = Abs(AnimDistance) / pnlSidebar.Width
		AnimDuration = sbCloseDuration * PctStillToMove
		sbAnimInProgress = CLOSING
	Else
		AnimDistance = CalcDistance(FROM_OPEN)
		PctStillToMove = Abs(AnimDistance) / pnlSidebar.Width
		AnimDuration = sbOpenDuration * PctStillToMove
		sbAnimInProgress = OPENING
	End If
	If AnimDistance = 0 Then
		' No distance -> no need for an animation
		sbAnimInProgress = 0
		TriggerFinalEvent
		Return
	End If

	pnlSidebarStartX = pnlSidebar.Left
	pnlSidebarStartY = pnlSidebar.Top
	pnlContentStartX = pnlContent.Left
	pnlContentStartY = pnlContent.Top

	' Animates the views
	sbStopAnim = False
	Dim Progression As Float
	Dim EndTime, DeltaTime As Long
	EndTime = DateTime.Now + AnimDuration
	Do While DateTime.Now < EndTime
		DeltaTime = EndTime - DateTime.Now
		Animate(Power(1 - (DeltaTime / AnimDuration), sbInterpolator) * AnimDistance)
		If SubExists(sbModule, sbSubMove) Then
			' Triggers an onMove event
			CallSub2(sbModule, sbSubMove, Not(Close))
		End If
		DoEvents 'Processes the draw messages and keeps the UI responsive
		If sbStopAnim Then
			DeltaTime = 0
			Exit
		End If
	Loop
	If DeltaTime <> 0 Then Animate(AnimDistance)

	sbAnimInProgress = 0
	TriggerFinalEvent
End Sub
#End Region

'Opens the sidebar
Public Sub OpenSidebar
	If sbAnimInProgress = CLOSING Then
		' Stops the current closing animation to start an opening animation
		sbStopAnim = True
		CallSubDelayed2(Me, "AnimateSidebar", OPEN_ANIM)
	Else If sbAnimInProgress = 0 Then
		' Starts an opening animation if there's not already one running
		AnimateSidebar(OPEN_ANIM)
	End If
End Sub

'Closes the sidebar
Public Sub CloseSidebar
	If sbAnimInProgress = OPENING Then
		' Stops the current opening animation to start a closing animation
		sbStopAnim = True
		CallSubDelayed2(Me, "AnimateSidebar", CLOSE_ANIM)
	Else If sbAnimInProgress = 0 Then
		' Starts a closing animation if there's not already one running
		AnimateSidebar(CLOSE_ANIM)
	End If
End Sub

'Is the sidebar currently visible ?
Public Sub IsSidebarVisible As Boolean
	Return sbIsVisible
End Sub

#Region Button, handle & swipe gesture
'Sets the button used to open or close the sidebar
'The button can be a Panel, an ImageView, a Button...
'It must be initialized and on screen.
Public Sub SetOpenCloseButton(Btn As View)
	If Btn = Null Then Return
	Dim r As Reflector
	r.Target = Btn
	r.SetOnClickListener("Btn_Click")
End Sub

Private Sub Btn_Click(ViewTag As Object)
	If IsSidebarVisible Then
		If sbAnimInProgress = CLOSING Then
			OpenSidebar
		Else
			CloseSidebar
		End If
	Else
		OpenSidebar
	End If
End Sub

'Adds the handle used to open or close the sidebar
'The handle can be a Panel, an ImageView, a Button...
'It must be initialized but not yet on screen.
'<B>Position</B> = top position if the sidebar is on the left or the right, left position otherwise
'<B>FinalMovement</B>:<I>
'0 = the sidebar can stay partially open/closed. There's no final movement.
'1 = the sidebar cannot stay partially open/closed. The final movement is an opening if the last movement was an opening movement, otherwise it is a closing.
'2 = the sidebar cannot stay partially open/closed. The final movement is an opening if the visible size of the sidebar >= 50% of its total size, otherwise it is a closing.</I>
Public Sub AddOpenCloseHandle(Hdl As View, Position As Int, Width As Int, Height As Int, FinalMovement As Byte)
	If Hdl = Null Then Return

	sbHandle = Hdl
	Select sbPosition
		Case 0 'LEFT
			sbParent.AddView(Hdl, pnlContent.Left, Position, Width, Height)
		Case 1 'RIGHT
			sbParent.AddView(Hdl, pnlContent.Left + pnlContent.Width - Width, Position, Width, Height)
		Case 2 'TOP
			sbParent.AddView(Hdl, Position, pnlContent.Top, Width, Height)
		Case 3 'BOTTOM
			sbParent.AddView(Hdl, Position, pnlContent.Top + pnlContent.Height - Height, Width, Height)
	End Select
	sbFinalMovement = FinalMovement

	Dim r As Reflector
	r.Target = Hdl
	r.SetOnTouchListener("Gesture_onTouch")
End Sub

'Enables the swipe gesture to open/close the sidebar
'<B>GestureAreaSize</B> = size of the gesture area
'The gesture area is an area above the frontier between the sidebar and the content panel where the gesture is expected.
'This area consumes all Touch events.
'<B>FinalMovement</B>:<I>
'0 = the sidebar can stay partially open/closed. There's no final movement.
'1 = the sidebar cannot stay partially open/closed. The final movement is an opening if the last movement was an opening movement, otherwise it is a closing.
'2 = the sidebar cannot stay partially open/closed. The final movement is an opening if the visible size of the sidebar >= 50% of its total size, otherwise it is a closing.</I>
Public Sub EnableSwipeGesture(Enabled As Boolean, GestureAreaSize As Int, FinalMovement As Byte)
	If Not(Enabled) Then
		If pnlGesture.IsInitialized Then pnlGesture.RemoveView
		pnlGesture = Null
		Return
	End If

	If pnlGesture.IsInitialized Then
		Select sbPosition
			Case 0 'LEFT
				pnlGesture.SetLayout(pnlContent.Left - (GestureAreaSize / 2), 0, GestureAreaSize, getParentHeight)
			Case 1 'RIGHT
				pnlGesture.SetLayout(pnlContent.Left + pnlContent.Width - (GestureAreaSize / 2), 0, GestureAreaSize, getParentHeight)
			Case 2 'TOP
				pnlGesture.SetLayout(0, pnlContent.Top - (GestureAreaSize / 2), getParentWidth, GestureAreaSize)
			Case 3 'BOTTOM
				pnlGesture.SetLayout(0, pnlContent.Top + pnlContent.Height - (GestureAreaSize / 2), getParentWidth, GestureAreaSize)
		End Select
	Else
		pnlGesture.Initialize("")
		Select sbPosition
			Case 0 'LEFT
				sbParent.AddView(pnlGesture, pnlContent.Left - (GestureAreaSize / 2), 0, GestureAreaSize, getParentHeight)
			Case 1 'RIGHT
				sbParent.AddView(pnlGesture, pnlContent.Left + pnlContent.Width - (GestureAreaSize / 2), 0, GestureAreaSize, getParentHeight)
			Case 2 'TOP
				sbParent.AddView(pnlGesture, 0, pnlContent.Top - (GestureAreaSize / 2), getParentWidth, GestureAreaSize)
			Case 3 'BOTTOM
				sbParent.AddView(pnlGesture, 0, pnlContent.Top + pnlContent.Height - (GestureAreaSize / 2), getParentWidth, GestureAreaSize)
		End Select

		Dim r As Reflector
		r.Target = pnlGesture
		r.SetOnTouchListener("Gesture_onTouch")
	End If
	sbFinalMovement = FinalMovement
End Sub
#End Region

Private Sub CalcDistance(FromOpen As Boolean) As Int
	' Calculates the distance between the current position and the position to reach
	Select sbPosition
		Case 0 'LEFT
			If sbAnimType = 2 Then
				If FromOpen Then
					Return - pnlSidebar.Left
				Else
					Return - pnlSidebar.Left - pnlSidebar.Width
				End If
			Else
				If FromOpen Then
					Return pnlSidebar.Width - pnlContent.Left
				Else
					Return - pnlContent.Left
				End If
			End If
		Case 1 'RIGHT
			If sbAnimType = 2 Then
				If FromOpen Then
					Return - pnlSidebar.Left + pnlContent.Width - pnlSidebar.Width
				Else
					Return - pnlSidebar.Left + pnlContent.Width
				End If
			Else
				If FromOpen Then
					Return - pnlSidebar.Width - pnlContent.Left
				Else
					Return - pnlContent.Left
				End If
			End If
		Case 2 'TOP
			If sbAnimType = 2 Then
				If FromOpen Then
					Return - pnlSidebar.Top
				Else
					Return - pnlSidebar.Top - pnlSidebar.Height
				End If
			Else
				If FromOpen Then
					Return pnlSidebar.Height - pnlContent.Top
				Else
					Return - pnlContent.Top
				End If
			End If
		Case 3 'BOTTOM
			If sbAnimType = 2 Then
				If FromOpen Then
					Return - pnlSidebar.Top + pnlContent.Height - pnlSidebar.Height
				Else
					Return - pnlSidebar.Top + pnlContent.Height
				End If
			Else
				If FromOpen Then
					Return - pnlSidebar.Height - pnlContent.Top
				Else
					Return - pnlContent.Top
				End If
			End If
	End Select
End Sub

Private Sub Gesture_onTouch(ViewTag As Object, Action As Int, X As Float, Y As Float, MotionEvent As Object) As Boolean
	If Action = 0 Then
		sbStopAnim = True
		sbStartX = X
		sbStartY = Y

	Else If Action = 2 Then
		' Moves the views to follow the finger
		Dim OldPos As Int
		Select sbPosition
			Case 0 'LEFT
				If sbAnimType = 2 Then
					OldPos = pnlSidebar.Left
					pnlSidebar.Left = Max(-pnlSidebar.Width, Min(pnlSidebar.Left + X - sbStartX, 0))
					If sbHandle.IsInitialized Then sbHandle.Left = sbHandle.Left - OldPos + pnlSidebar.Left
					If pnlGesture.IsInitialized Then pnlGesture.Left = pnlGesture.Left - OldPos + pnlSidebar.Left
				Else
					OldPos = pnlContent.Left
					pnlContent.Left = Max(0, Min(pnlContent.Left + X - sbStartX, pnlSidebar.Width))
					If sbAnimType = 1 Then pnlSidebar.Left = pnlContent.Left - pnlSidebar.Width
					If sbHandle.IsInitialized Then sbHandle.Left = sbHandle.Left - OldPos + pnlContent.Left
					If pnlGesture.IsInitialized Then pnlGesture.Left = pnlGesture.Left - OldPos + pnlContent.Left
				End If
				sbIsOpening = X > sbStartX
			Case 1 'RIGHT
				If sbAnimType = 2 Then
					OldPos = pnlSidebar.Left
					pnlSidebar.Left = Max(pnlContent.Width - pnlSidebar.Width, Min(pnlSidebar.Left + X - sbStartX, pnlContent.Width))
					If sbHandle.IsInitialized Then sbHandle.Left = sbHandle.Left - OldPos + pnlSidebar.Left
					If pnlGesture.IsInitialized Then pnlGesture.Left = pnlGesture.Left - OldPos + pnlSidebar.Left
				Else
					OldPos = pnlContent.Left
					pnlContent.Left = Max(-pnlSidebar.Width, Min(pnlContent.Left + X - sbStartX, 0))
					If sbAnimType = 1 Then pnlSidebar.Left = pnlContent.Left + pnlContent.Width
					If sbHandle.IsInitialized Then sbHandle.Left = sbHandle.Left - OldPos + pnlContent.Left
					If pnlGesture.IsInitialized Then pnlGesture.Left = pnlGesture.Left - OldPos + pnlContent.Left
				End If
				sbIsOpening = X < sbStartX
			Case 2 'TOP
				If sbAnimType = 2 Then
					OldPos = pnlSidebar.Top
					pnlSidebar.Top = Max(-pnlSidebar.Height, Min(pnlSidebar.Top + Y - sbStartY, 0))
					If sbHandle.IsInitialized Then sbHandle.Top = sbHandle.Top - OldPos + pnlSidebar.Top
					If pnlGesture.IsInitialized Then pnlGesture.Top = pnlGesture.Top - OldPos + pnlSidebar.Top
				Else
					OldPos = pnlContent.Top
					pnlContent.Top = Max(0, Min(pnlContent.Top + Y - sbStartY, pnlSidebar.Height))
					If sbAnimType = 1 Then pnlSidebar.Top = pnlContent.Top - pnlSidebar.Height
					If sbHandle.IsInitialized Then sbHandle.Top = sbHandle.Top - OldPos + pnlContent.Top
					If pnlGesture.IsInitialized Then pnlGesture.Top = pnlGesture.Top - OldPos + pnlContent.Top
				End If
				sbIsOpening = Y > sbStartY
			Case 3 'BOTTOM
				If sbAnimType = 2 Then
					OldPos = pnlSidebar.Top
					pnlSidebar.Top = Max(pnlContent.Height - pnlSidebar.Height, Min(pnlSidebar.Top + Y - sbStartY, pnlContent.Height))
					If sbHandle.IsInitialized Then sbHandle.Top = sbHandle.Top - OldPos + pnlSidebar.Top
					If pnlGesture.IsInitialized Then pnlGesture.Top = pnlGesture.Top - OldPos + pnlSidebar.Top
				Else
					OldPos = pnlContent.Top
					pnlContent.Top = Max(-pnlSidebar.Height, Min(pnlContent.Top + Y - sbStartY, 0))
					If sbAnimType = 1 Then pnlSidebar.Top = pnlContent.Top + pnlContent.Height
					If sbHandle.IsInitialized Then sbHandle.Top = sbHandle.Top - OldPos + pnlContent.Top
					If pnlGesture.IsInitialized Then pnlGesture.Top = pnlGesture.Top - OldPos + pnlContent.Top
				End If
				sbIsOpening = Y < sbStartY
		End Select
		sbIsVisible = (CalcDistance(FROM_CLOSE) <> 0)

		' Triggers an onMove event
		If SubExists(sbModule, sbSubMove) Then
			If sbIsOpening Then
				CallSub2(sbModule, sbSubMove, True)
			Else
				CallSub2(sbModule, sbSubMove, False)
			End If
		End If

	Else If Action = 1 Then
		' Is there a final movement to do?
		If sbFinalMovement > 0 And sbIsVisible Then
			If sbFinalMovement = 2 Then sbIsOpening = Abs(CalcDistance(FROM_CLOSE)) >= Abs(CalcDistance(FROM_OPEN))
			If sbIsOpening Then
				CallSubDelayed2(Me, "AnimateSidebar", OPEN_ANIM)
			Else
				CallSubDelayed2(Me, "AnimateSidebar", CLOSE_ANIM)
			End If
		Else
			TriggerFinalEvent
		End If
	End If
	Return True
End Sub

Sub TriggerFinalEvent
	' Triggers an onFullyOpen or onFullyClosed event
	If CalcDistance(FROM_OPEN) = 0 And SubExists(sbModule, sbSubFullyOpen) Then
		CallSub(sbModule, sbSubFullyOpen)
	Else If CalcDistance(FROM_CLOSE) = 0 And SubExists(sbModule, sbSubFullyClosed) Then
		CallSub(sbModule, sbSubFullyClosed)
	End If
End Sub
