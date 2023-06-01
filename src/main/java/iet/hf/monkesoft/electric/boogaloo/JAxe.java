package iet.hf.monkesoft.electric.boogaloo;

import javax.swing.*;
import java.awt.event.MouseEvent;

/**
 * A balta felszerelÃ©s megjelenÃ­tÃ©sÃ©rt felelÅ‘s osztÃ¡ly. A baltÃ¡t nem lehet equip-elni. 
 * Az inventoryban kell rÃ¡kattintani, hogy hasznÃ¡lni tudjuk. 
 * */
public class JAxe extends JEquipment{
	private Axe axe;
	private JButton bUse = new JButton("Use");
	private View view;
	public JAxe(View view, Interface iface, JInventory inventory, Axe axe, boolean eqiupped, String imgName) {
		super(iface, inventory, axe, eqiupped, imgName);
		this.axe = axe;
		this.view = view;
		
		bUse.addMouseListener(this);
		bUse.setAlignmentX(CENTER_ALIGNMENT);
		bDiscard.addMouseListener(this);
	}

	/**
	 * Kirajzolja a baltÃ¡t
	 * */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}
	@Override
	protected void onClick()
	{
		inventory.setOptionButtons(new JButton[] {bUse, bDiscard});
	}
	/**
	 * Use gombra kattintva hasznÃ¡ljuk a fejszÃ©t egy virolÃ³guson.
	 * */
	public void onUseClick() {
		view.onAxeUseClick(axe);
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource() == this)
			onClick();
		else if(e.getSource() == bUse)
			onUseClick();
		else if(e.getSource() == bEquip)
			doNothing();
		else 
			super.mouseReleased(e);
	}

	private void doNothing() {
		
		/**
		 * don’t care
didn’t ask
cry about it
who asked
stay mad
get real
L
bleed
mald seethe cope harder
dilate
incorrect
hoes mad
pound sand
basic skill issue
typo
ratio
ur dad left
you fell off
no u
the audacity
triggered
repelled
ur a minor
k.
any askers
get a life
ok and?
cringe
copium
go outside
touch grass
kick rocks
quote tweet
think again
not based
not funny didn’t laugh
social credits -999, 999, 999, 999
get good
reported
ad hominem
ok boomer
small pp
ur allergic to sunlight
GG!
get rekt
trolled
your loss
muted
banned
kicked
permaban
useless
i slept with ur mom
yo momma
yo momma so fat
redpilled
no bitches allowed
i said it better
tiktok fan
get a life
unsubscribed
plundered
go tell reddit
donowalled
simp
get sticked bug LOL
talk nonsense
trump supporter
your’re a full time discord mod
you’re*
grammar issue
nerd
get clapped
kys
lorem ipsum dolor sit amet
go outside
bleach
lol
gay
retard
autistic
reported
ask deez
ez clap
straight cash
idgaf
ratio again
stay mad
read FAQ
youre lost
you “re”
stay pressed
reverse double take back
pedophile
cancelled
done for
don't give a damn
get a job
sus
baka
sussy baka
get blocked
mad free
freer than air
furry
rip bozo
you're a (insert stereotype)
slight_smile
aired
cringe again
Super Idol的笑容
mad cuz bad
my pronouns are xe, xem & xyr
irrelevant
deal with it
screencapped your bio
karen/kyle
jealous
you're deaf
balls
i'll be right back
go ahead whine about it
not straight
eat paper
you lose
count to three
your problem
no one cares
log off
don't care even more
sex offender
sex defender
get religion
not okay
glhf
NFT owner
you make bad memes
problematic
fall in line
dog water
you look like a wall
you don’t know 2
2 with yo head ass
you are going to my cringe compilation
you can’t count to five
try again
you failed kindergarten
rickrolled
no lifer
guten freunden schickt man einen deutschen panzer
you have a anime profile picture
an*
fatherless
motherless
sisterless
brotherless
orphan
you can't catch this ratio
catch some bitches
I don't care about your opinion
genshin player
you dress like garbage
日本語がお上手ですね
get fucked
you can’t understand what the word intelligence means with your dumb ass
you have hair
queued
put some thought into what you're going to do with that
stfu
go to bed
yes, i'm taller than you
i think your joke is funny
i rejected your mother's advances
marooned
you can’t read
I win
final ratio
		 * 
		 */
		
	}
	
}
