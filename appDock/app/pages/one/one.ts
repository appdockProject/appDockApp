import {Page, NavController} from 'ionic-angular';
import {HomePage} from '../home/home';
import {TutorialPage} from '../tutorial/tutorial';
import {FeedbackPage} from '../feedback/feedback';
import {SocialPage} from '../social/social';
import {DevPage} from '../dev/dev';
import {TwoPage} from '../two/two';
import {ThreePage} from '../three/three';
import {FourPage} from '../four/four';
import {AboutPage} from '../about/about';
/*
  Generated class for the OnePage page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Page({
  templateUrl: 'build/pages/one/one.html',
})
export class OnePage {
  homePage = HomePage;
  tutorialPage = TutorialPage;
  feedbackPage = FeedbackPage;
  socialPage = SocialPage;
  devPage = DevPage;
  twoPage = TwoPage;
  threePage = ThreePage;
  fourPage = FourPage;
  aboutPage = AboutPage;
  constructor(public nav: NavController) {}
}
