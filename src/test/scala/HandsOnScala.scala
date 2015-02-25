import part1_1_first_steps._
import part1_2_sequel._
import part2_we_need_to_go_deeper._
import part3_cons_and_nil._
import part4_type_classes._
import part5_the_return_of_the_bag._
import part6_bonus_event_sourcing._

import org.scalatest._
import support.CustomStopper

trait HandsOn extends Suite {
  override def toString = "HandsOn"
}
//  override def run(testName: Option[String], args: Args): Status = {
//    if(args.stopper.stopRequested) {
//      FailedStatus
//    } else {
//      super.run(testName, args)
//    }
//  }
////  override def run(testName: Option[String], reporter: Reporter, stopper: Stopper, filter: Filter,
////                   configMap: Map[String, Any], distributor: Option[Distributor], tracker: Tracker) {
////    if(!CustomStopper.oneTestFailed)
////    super.run(testName, reporter, CustomStopper, filter, configMap, distributor, tracker)
////  }
//}

class HandsOnScala extends Sequential(
  new part1_1_first_steps,
  new part1_2_sequel,
  new part2_we_need_to_go_deeper,
  new part3_cons_and_nil,
  new part4_type_classes,
  new part5_the_return_of_the_bag,
  new part6_bonus_event_sourcing
) with HandsOn

class part1_1_first_steps extends Sequential(
    new e0_vars_vals,
    new e1_classes,
    new e2_case_classes,
    new e3_boucle_for
) with HandsOn

class part1_2_sequel extends Sequential(
    new e4_listes,
    new e5_maps,
    new e6_sets,
    new e7_option,
    new e8_fonctions_de_plus_haut_niveau,
    new e9_extracteurs_et_patterns
) with HandsOn


class part2_we_need_to_go_deeper extends Sequential(
    new e0_une_histoire_de_sacs,
    new e1_un_sac_comme_generique,
    new e2_un_sac_algebrique,
    new e3_un_sac_covariant
) with HandsOn


class part3_cons_and_nil extends Sequential(
    new e0_list,
    new e1_bonus_stream
) with HandsOn


class part4_type_classes extends Sequential(
    new testJson,
    new client.testJsonClient
) with HandsOn


class part5_the_return_of_the_bag extends Sequential(
    new e0_une_mise_en_abime,
    new e1_un_peu_plus_generique,
    new e2_un_peu_plus_algebrique,
    new e3_on_a_besoin_de_la_covariance
) with HandsOn


class part6_bonus_event_sourcing extends Sequential(
    new testEventSourcing
) with HandsOn

