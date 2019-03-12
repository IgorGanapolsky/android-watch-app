###### Journey 1a (developer estimate 8 hoursTotal):
Given there are no timers created nor active timer running/paused,
When I launch the app
https://marvelapp.com/7iab626/screen/51207910

Then I see the screen telling me to create a new timer
https://marvelapp.com/7iab626/screen/51207911

**Day 1 release

-----------------------------------------------------------------------

###### Journey 1b (dev est. 16 hoursTotal):
Given I am on the next timer creation screen (swipe right-to-left)
https://marvelapp.com/7iab626/screen/51207912

When I set the timer details,
Then I should have an option to set these vibration patterns: 
	1 buzzCount (5 secondsTotal), 3 buzzes (3 secondsTotal each), 5 buzzes (2 secondsTotal each), 1 long buzzCount (20 secondsTotal)
https://marvelapp.com/7iab626/screen/51207913
NOTE - user sets timer details with standard Android time picker
*Day 1 release

-----------------------------------------------------------------------

###### Journey 1c (est. 8 hoursTotal):
Given I am on the next timer creation screen (swipe right-to-left),
https://marvelapp.com/7iab626/screen/51207919

When I set the timer details,
Then I should have an option to set how many times the timer repeats
https://marvelapp.com/7iab626/screen/51207926

**Day 1 release
----> Question from Yuliia: "User sets timer details (https://marvelapp.com/7iab626/screen/51207912) as 27min and 2 repeats and run it.
Does that mean what timer will vibrate first time in 27 min with possibility to stop or go to next 27min interval?"
----> Answer from Igor: "You are correct. The timer should also auto-start the next interval (after it finishes vibrating)."

-----------------------------------------------------------------------

###### Journey 2 (est. 8 hoursTotal):
Given there is at least one timer created,
https://marvelapp.com/7iab626/screen/51207928

When I launch the app,
Then I see the screen with a list of created timers
https://marvelapp.com/7iab626/screen/51207929

**Day 1 release

-----------------------------------------------------------------------

###### Journey 3 (est. 4 hoursTotal):
Given there is one active timer running or paused,
https://marvelapp.com/7iab626/screen/51207930

When I launch the app,
Then I see the active timer screen showing running/paused state
https://marvelapp.com/7iab626/screen/51207932

**Day 1 release

-----------------------------------------------------------------------

###### Journey 5 (est. 4 hoursTotal):
Given there is an active timer running or paused,
When I press Stop button on the active timer screen,
Then the timer should stop and I am taken to the screen with a list of created timers
NOTE - this is the same UI as Case 4B

**Day 1 release

-----------------------------------------------------------------------

###### Journey 6 (est. 4 hoursTotal):
Given there is an active timer running or paused,
https://marvelapp.com/7iab626/screen/51207939

When I press Pause button on the active timer screen,
Then the timer progress on that screen should blink to indicate that it is paused
https://marvelapp.com/7iab626/screen/51207940
(the time indicator H:M:S should blink)
NOTE - ‘blinking’ effect isn't shown in MarvelApp

**Day 1 release

-----------------------------------------------------------------------

###### Journey 8 (est. 4 hoursTotal):
Given that I am on an active timer screen,
https://marvelapp.com/7iab626/screen/51207946

When I swipe left-to-right, 
Then I should be taken to the screen with a list of created timers
https://marvelapp.com/7iab626/screen/51207948

**Day 1 release

-----------------------------------------------------------------------

###### Journey 9 (est. 4 hoursTotal):
Given that I am on the screen with a list of created timers,
https://marvelapp.com/7iab626/screen/51207950
When I click on a timer in the list, 
Then I should be taken to the screen for that timer
https://marvelapp.com/7iab626/screen/51207951

**Day 1 release

-----------------------------------------------------------------------

###### Journey 11 (est. 4 hoursTotal):
Given that I am on the screen with a list of created timers,
When I click on a timer that isn't running/paused,
Then I see the screen allowing me to modify the timer details, as well as start it
NOTE - same UI as cases 9 & 13

**Day 1 release

-----------------------------------------------------------------------

###### Journey 13 (est. 16 hoursTotal):
Given that I select a timer from the list,
When I look at that timer's detail screen,
Then I should see a 'Configure' button that allows me to modify this timer's details
  (all fields are editable just like when creating a new timer)
NOTE - same UI as cases 9 & 11

**Day 1 release

-----------------------------------------------------------------------

DAY 1 TOTAL DEVELOPER ESTIMATE: 80 hrs
