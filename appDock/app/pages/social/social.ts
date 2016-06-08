import {Page, NavController} from 'ionic-angular';
import {DevPage} from '../dev/dev';
import {TutorialPage} from '../tutorial/tutorial';
import {FeedbackPage} from '../feedback/feedback';
import {HomePage} from '../home/home';
import {OnePage} from '../one/one';
import {TwoPage} from '../two/two';
import {ThreePage} from '../three/three';
import {FourPage} from '../four/four';
import {AboutPage} from '../about/about';
/*
  Generated class for the SocialPage page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Page({
  templateUrl: 'build/pages/social/social.html',
})
export class SocialPage {
  devPage = DevPage;
  tutorialPage = TutorialPage;
  feedbackPage = FeedbackPage;
  homePage = HomePage;
  onePage = OnePage;
  twoPage = TwoPage;
  threePage = ThreePage;
  fourPage = FourPage;
  aboutPage = AboutPage;
  constructor(public nav: NavController) {}
}
