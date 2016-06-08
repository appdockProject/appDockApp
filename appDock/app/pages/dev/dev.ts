import {Page, NavController} from 'ionic-angular';
import {HomePage} from '../home/home';
import {TutorialPage} from '../tutorial/tutorial';
import {FeedbackPage} from '../feedback/feedback';
import {SocialPage} from '../social/social';
import {OnePage} from '../one/one';
import {TwoPage} from '../two/two';
import {ThreePage} from '../three/three';
import {FourPage} from '../four/four';
import {AboutPage} from '../about/about';
/*
  Generated class for the DevPage page.

  See http://ionicframework.com/docs/v2/components/#navigation for more info on
  Ionic pages and navigation.
*/
@Page({
  templateUrl: 'build/pages/dev/dev.html',
})
export class DevPage {
  homePage = HomePage;
  tutorialPage = TutorialPage;
  feedbackPage = FeedbackPage;
  socialPage = SocialPage;
  onePage = OnePage;
  twoPage = TwoPage;
  threePage = ThreePage;
  fourPage = FourPage;
  aboutPage = AboutPage;
  constructor(public nav: NavController) {}
}
