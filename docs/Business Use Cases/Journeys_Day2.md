###### Journey 1 (dev est. 16 hours):
Given I am on the timer creation screen
https://marvelapp.com/7iab626/screen/51207912

When I set the timer details,
Then there should purple circle indicator dot.
Implement a save button on the timer creation screens.
https://marvelapp.com/7iab626/screen/51207913

*Day 2 release

-----------------------------------------------------------------------

###### Journey 2 (est. 8 hours):
Given I am on the timer creation screen,
https://marvelapp.com/7iab626/screen/51207919

When I set the timer details,
Then I should have an option to set the interval between each repetition
https://marvelapp.com/7iab626/screen/51207926

-----------------------------------------------------------------------

###### Journey 3 (est. 8 hours):
Given there are multiple active timers running or paused,
https://marvelapp.com/7iab626/screen/51207933

When I launch the app,
Then I am taken to the screen with timers list, and active timers are highlighted (indicating running/paused states on that list)
https://marvelapp.com/7iab626/screen/51207934
Circle indicator should update according to elapsed time in current interval
NOTE - this is the same UI as Case 2

*Day 2 release

-----------------------------------------------------------------------

###### Journey 4 (est. 4 hours):
Given there is an active timer(s) running or paused,
https://marvelapp.com/7iab626/screen/51207935

When I navigate to the screen with a list of created timer(s),
Then I should see the active timer(s) highlighted on that list
https://marvelapp.com/7iab626/screen/51207937
NOTE - This would be a swipe to the right

**Day 2 release

-----------------------------------------------------------------------

###### Journey 5 (est. 8 hours):
Given there is an active timer(s) running or paused,
https://marvelapp.com/7iab626/screen/51207941

When I try to start another timer,
https://marvelapp.com/7iab626/screen/51207942

Then it should start and run simultaneously with the active timer(s) in the background
https://marvelapp.com/7iab626/screen/51207943
 (this feature isn't a UI effect, it is just two timers running in the background.  User
  will then see the running timers in the timers list)

**Day 2 release

-----------------------------------------------------------------------

###### Journey 6 (est. 8 hours):
Given that I am on the screen with a list of created timers,
https://marvelapp.com/7iab626/screen/51207952
When I long-press on a timer,
Then that list item should reveal a swipe menu with a delete button
https://marvelapp.com/7iab626/screen/51207953

**Day 2 release

-----------------------------------------------------------------------

###### Journey 7 (est. 8 hours):
Given that I am on the screen with a list of created timers,
When I try to delete a timer,
Then a confirmation dialog should appear
https://marvelapp.com/7iab626/screen/51207954
NOTE - same UI as 10A above - just click on the delete

**Day 2 release

-----------------------------------------------------------------------

###### Journey 8 (est. 4 hours):
Given that a running timer finished its countdown and is vibrating,
https://marvelapp.com/7iab626/screen/51207955

When I press on the Next button,
Then the timer transitions to the next interval (or stops if it only had one interval)
https://marvelapp.com/7iab626/screen/51207956

**Day 2 release

-----------------------------------------------------------------------

###### Journey 9 (est. 4 hours):
Given that a running timer finished its countdown and is vibrating,
https://marvelapp.com/7iab626/screen/51207957

When I press on any hardware button on the watch,
Then the timer transitions to the next interval (or stops if it only had one interval)
https://marvelapp.com/7iab626/screen/51207958

**Day 2 release

-----------------------------------------------------------------------

###### Journey 10 (est. 8 hours):
Given that a running timer finished its countdown and is vibrating,
When I rotate a back/forth wrist gesture three times,
Then the timer transitions to the next interval (or stops if it only had one interval)
NOTE - this is a gesture I can ‘do’ in MarvelApp

**Day 2 release

-----------------------------------------------------------------------

###### Journey 11 (est. 8 hours):
Given that I am on the smart watch face screen (watch face),
https://marvelapp.com/7iab626/screen/51207961

When try to add a complication to the watch face,
Then I get the option to add this Vibrating Watch Timer app
https://marvelapp.com/7iab626/screen/51208291
https://marvelapp.com/7iab626/screen/51208294

Click the Timer logo in 2nd screen to add it to the watch face

**Day 2 release

-----------------------------------------------------------------------


###### Journey 12 (est. 4 hours):
Given that I have added a complication for my app,
When I view the complication,
Then I see the ongoing timer progress (if there is One ongoing timer), otherwise I just see the shortcut to take me to my timer list
https://marvelapp.com/7iab626/screen/51207964
NOTE - this shows the timer logo - with a timer in progress

**Day 2 release

-----------------------------------------------------------------------

DAY 2 TOTAL DEVELOPER ESTIMATE: 80 hrs
