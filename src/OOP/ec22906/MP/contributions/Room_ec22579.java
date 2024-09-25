package OOP.ec22906.MP.contributions;

import java.util.Random;

public class Room_ec22579 extends Room
{
    Item brick = new Item("Brick");
    Item glassEye = new Item("Glass Eye");
    Item purpleVial = new Item("Purple Vial");
    char [] options = new char[]{'a','b','c','d'};
    static int door_visited = 0;
    static int organ_visited = 0;
    static int box_visited = 0;
    char option;

    public Direction visit (Visitor visitor, Direction d){



        if ((door_visited==0)&&(organ_visited==0)&&(box_visited==0))
        {
            visitor.tell("You have found one of the hidden rooms that became blocked off after the earthquake. As you step in you can feel the cold breeze entering from the shattered stained glass window.A burnt scent lingering in the air");
            visitor.tell("Observing the room, you can see a pump organ with keys that look as if they're being played.");
            visitor.tell("Towards your left there is a blue door. Hard pounding knocks can be heard from the other side getting louder by the second");
            visitor.tell("Towards your right lies a single ottoman, the fabric faded with time and coated in dust. Sitting perfectly on top lies a porcelain box with not a single spec of dust.");
            visitor.tell("What would you like to investigate?");

        }

        else
        {
            visitor.tell("Welcome back!");
            visitor.tell("What would you like to investigate this time?");
        }
        option = visitor.getChoice("(a) Pump Organ  (b) Blue door  (c) Porcelain box (d)Leave the room", options);


        while(option != 'd')
        {

            if (option == 'a')
            {
                if (organ_visited == 1)
                {
                    visitor.tell("You have already investigated this last time. There is nothing else left to see. ");
                }

                else
                {
                    visitor.tell("You advance toward the pump organ, as you get closer you can clearly see the keys moving, however no sound can be heard.");
                    visitor.tell("You step on a creaky floorboard and a sudden stillness falls across the room, the pounding stopped, the breeze ceased and the keys motionless");
                    visitor.tell("As you're observing the organ, you press a key, the low thundering sound ringing through the air and then a thud.");
                    visitor.tell("A glass eye comes rolling out from the organ");

                    if(visitor.giveItem(glassEye))
                    {
                        visitor.tell("You keep the glass eye in your pocket");
                    }
                    else
                    {
                        visitor.tell("You see the pupil shift towards you and run out the room");
                        int gold_taken = visitor.takeGold(4);

                        if ((gold_taken<4)&&(gold_taken>0))
                        {
                            visitor.tell("As you run out, you drop all your gold");
                        }
                        else if (gold_taken ==4)
                        {
                            visitor.tell("As you run out, you drop 4 gold coins");
                        }

                        return Direction.opposite(d);
                    }

                    organ_visited =1;
                }
            }


            else if (option == 'b')
            {
                if (door_visited==1)
                {
                    visitor.tell("I see you were brave enough to come back again. There is no more pounding at the door, but it now looks very battered");
                    visitor.tell("You approach the door and swing it open. ");
                    visitor.tell("You balance yourself from falling as in front of you is a 10 foot drop and a view of the entire estate. ");
                    visitor.tell("For bravely coming back again, here's 10 coins.");
                    visitor.giveGold(10);
                    visitor.tell("Just before you leave, you see a brick on the ledge and decide to take it.");
                    visitor.giveItem(brick);
                    {
                        visitor.tell("However you are unable to pick it up, so you just leave it");
                    }
                    door_visited =2;
                }

                else if (door_visited==2)
                {
                    visitor.tell("There is nothing else left to investigate");
                }

                else
                {
                    visitor.tell("You move towards the banging, each step heavier than the last. As you step on the creaky floorboard, the sound vibrates through the room. The banging silenced. ");
                    visitor.tell("A prickling sensation builds up at the nape of your neck");
                    visitor.tell("The pounding comes back in full force, trying to break the door off its hinges");
                    visitor.tell("Your anxiety builds up and your fear overpowers you to flee the room.");
                    int gold_taken = visitor.takeGold(2);

                    if (gold_taken == 1)
                    {
                        visitor.tell("As you run out, you drop all your gold");
                    }
                    else if (gold_taken ==2)
                    {
                        visitor.tell("As you run out, you drop 2 gold coins");
                    }
                    door_visited = 1;

                    return Direction.opposite(d);
                }

            }



            else if (option =='c')
            {

                if (box_visited==3)
                {
                    visitor.tell("As you approach the box, it suddenly vanishes");
                    visitor.tell("Leaving behind a single sentence");
                    visitor.tell("You have chanced fate too many time!");
                    visitor.tell("You feel a hand on your foot and you are dragged out the room");
                    visitor.tell("You drop some coins on your way out, but cannot see how much");
                    visitor.takeGold(5);
                    return Direction.opposite(d);
                }

                else
                {
                    visitor.tell("Beside the box, you see a note");
                    visitor.tell("You have the chance to win some extra money and a mystery item");
                    visitor.tell("The note says:  ");
                    visitor.tell("place some coins within the box and get the chance to gain more money or gain an item");
                    visitor.tell("BEWARE you could also lose your money");
                    char [] yes_no_opt = new char[]{'y','n'};
                    option = visitor.getChoice("(y)yes (n)no ",yes_no_opt);

                    if (option == 'y')
                    {
                        visitor.tell("How much would you like to place");
                        char [] num_options = new char[]{'1','2','3','4','5','6','7','8','9'};
                        option = visitor.getChoice("pick a value from 0-9 ", num_options);
                        visitor.tell("You place "+option+" coins in the box and close the lid");


                        if (option =='0')
                        {
                            visitor.tell("Nothing happens");
                        }
                        //
                        else if ((option =='1')||(option =='2')||(option =='3')||(option =='4'))
                        {
                            visitor.tell("You open the box to find it empty");
                            visitor.tell("You lost your money");
                            visitor.takeGold(Character.getNumericValue(option));

                        }
                        else if ((option =='5')||(option =='6'))
                        {
                            if(visitor.hasIdenticalItem(purpleVial))
                            {
                                visitor.tell("You open the box to find nothing new");
                                visitor.tell("You did not win any extra money");
                                visitor.tell("But hey, i guess it's better than losing.");
                            }
                            else
                            {
                                visitor.tell("You open the box to find an item, a glass vial filled with shimmering purple liquid");
                                visitor.tell("The box also contains your original money");
                                visitor.giveItem(purpleVial);
                            }

                        }

                        else
                        {
                            Random random = new Random();
                            int coins_found = random.nextInt(11);
                            visitor.tell("You open the box to find that you have gained extra money");
                            visitor.tell("Upon counting you see that you have won "+coins_found+" extra coins");
                            visitor.giveGold(coins_found);
                        }

                        box_visited = box_visited +1;

                    }


                }

            }

            visitor.tell("What would you like to investigate next?");
            option = visitor.getChoice("(a) Pump Organ  (b) Blue door  (c) Porcelain box (d)Leave the room", options);

        }

        visitor.tell("Which direction would you like to leave from:");
        option = visitor.getChoice("(a) North  (b) East  (c) South (d)West", options);
        if (option == 'a')
        {
            return Direction.TO_NORTH;
        }
        else if (option == 'b')
        {
            return Direction.TO_EAST;
        }
        else if (option == 'c')
        {
            return Direction.TO_SOUTH;
        }
        else if (option == 'd')
        {
            return Direction.TO_WEST;
        }
        else
        {
            return Direction.opposite(d);
        }

    }
}
