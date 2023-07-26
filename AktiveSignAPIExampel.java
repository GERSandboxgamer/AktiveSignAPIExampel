package de.sbg.aktivesignbeispiel;

import de.sbg.unity.aktivesign.AktiveSign;
import de.sbg.unity.aktivesign.Events.TestSignEvent;
import de.sbg.unity.aktivesign.Objects.Tester.SignTester;
import de.sbg.unity.aktivesign.Objects.Tester.SignTester.SignTesterStatus;
import net.risingworld.api.Plugin;
import net.risingworld.api.events.EventMethod;
import net.risingworld.api.events.Listener;

public class AktiveSignBeispiel extends Plugin {

    private AktiveSign AS;

    @Override
    public void onEnable() {

        AS = (AktiveSign) getPluginByName("AktiveSign");
        //Is AktiveSign installed?
        if (AS != null) {
            // Add Sign
            AS.Sign.addSign("Exampel");
            AS.Sign.addUserSign("UserExampel");
        }

    }

    @Override
    public void onDisable() {

    }

    public class SignListener implements Listener {

        //Calls, if a player interact or write as sign
        @EventMethod
        public void onSignTestEvent(TestSignEvent event) {
            //.... Do Someting
            // Exampel:
            if (AS.Sign.isAktiveSign(event.getLine(1))) { //getLine(1) = Line 1;
                // Sign is a AktiveSign

                if (AS.Sign.isUserSign(event.getLine(1))) {
                    // Sign is UserSign
                    // If sign is UserSign, the sign is AktivSign, too.

                    if (event.getLine(1).equals("[UserExampel]")) {
                        //Do Something

                    }
                } else {
                    //Sign is normal AktiveSign

                    if (event.getLine(1).equals("[Exampel]")) {
                        if (event.getLine(2).toLowerCase().equals("exampelvalue")) {
                            if (!event.isInteraction()) {
                                //Player write the sign!
                                if (event.getPlayer().isAdmin()) {
                                    event.setSignTesterStatus(SignTester.SignTesterStatus.OK);
                                    //Player can write the sign
                                } else {
                                    event.setSignTesterStatus(SignTester.SignTesterStatus.Permission);
                                    //Player have not enouth permissions!
                                }
                            } else { // Player interact with sign
                                //Optinal:
                                SignTesterStatus st = AS.SignPermission.hasPermissionAndMoney(event.getPlayer(), event.getLine(3), event.getLine(4), event.isInteraction());
                                if (st != SignTesterStatus.Permission && st != SignTesterStatus.Money) {
                                    // Player has permission and enouth money!
                                    // Do Something
                                    event.setSignTesterStatus(SignTesterStatus.OK);
                                } else {
                                    //Player has not enouth permission or money!
                                    event.setSignTesterStatus(st);
                                }
                            }
                        } else {
                            event.setSignTesterStatus(SignTesterStatus.Misspelled);
                            //sign was misspelled
                        }
                    }
                    //Optinal: More Sign Commands
                }
            }
            event.setSignTesterStatus(SignTester.SignTesterStatus.Nothing);
        }

    }

}
