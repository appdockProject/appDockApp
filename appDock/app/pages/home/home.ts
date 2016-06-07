import {Page} from 'ionic-angular';
import {DevPage} from '../dev/dev';
import {TutorialPage} from '../tutorial/tutorial';
import {FeedbackPage} from '../feedback/feedback';
import {SocialPage} from '../social/social';
import {OnePage} from '../one/one';
import {TwoPage} from '../two/two';
import {ThreePage} from '../three/three';
import {FourPage} from '../four/four';
import {AboutPage} from '../about/about';

@Page({
  templateUrl: 'build/pages/home/home.html'
})
export class HomePage {
  devPage = DevPage;
  tutorialPage = TutorialPage;
  feedbackPage = FeedbackPage;
  socialPage = SocialPage;
  onePage = OnePage;
  twoPage = TwoPage;
  threePage = ThreePage;
  fourPage = FourPage;
  aboutPage = AboutPage;
  constructor() {

  }
}
