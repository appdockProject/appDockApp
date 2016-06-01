import {Page} from 'ionic-angular';
import {DevPage} from '../dev/dev';
import {TutorialPage} from '../tutorial/tutorial';
import {FeedbackPage} from '../feedback/feedback';
import {SocialPage} from '../social/social';

@Page({
  templateUrl: 'build/pages/home/home.html'
})
export class HomePage {
  devPage = DevPage;
  tutorialPage = TutorialPage;
  feedbackPage = FeedbackPage;
  socialPage = SocialPage;
  constructor() {

  }
}
