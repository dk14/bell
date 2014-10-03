#Bell's game

This is a simulation of SHCH bell's inequality violation, assuming:

- local realism - entangled particles are sharing some hidden parameters (see Model.scala for theoretical explanation)
- simultaneity is just a special case - it's possible to violate SHCH based on sequence numbers instead of timetags
- particle knows about measurement
- optional1: particle knows about her seqNumber or timeTag - it allows to build strategy in which number of measurements does not affect much measured S (number of switches does).
- optional2 (not simulated): entanglement with detector - if particle knows which detector settings were used before (or even after) - it can build more effective strategy than simulated here. If state of switch correlates with hidden parameter then it's possible to built strategy which always will give us S > 2 or even S = 4; it will be still local, deterministic and non-communicate.

It may not correlate with practical experiments, but at least shows that it's logically possible to set up an experiment which violate SHCH with local reality assumption and without any communication between participants or particles. So, such kind of models may lead to appropriate hidden parameters based model (maybe even with truly random hidden parameters).  

Note, that there is also no communication between concrete particles of same detector.

How to use:

    # please, install Git and Java 8 before
    git clone https://github.com/dk14/bell.git
    cd bell
    # Save 1000 particle entanglements to the e.txt file:
    # you can copy it to another computer to ensure non-communication
    java -jar bell.jar entangle 1000 > e.txt 
    # measure A particles 1000 times using random settings for detector
    # results will be saved into A.log
    java -jar bell.jar measureA e.txt 1000 random > A.log
    # same for B -- you may run it on another computer
    java -jar bell.jar measureB e.txt 1000 random > B.log
    # please, move A.log and B.log to the same computer if they were separated
    # use 'analize' command to calculate correlations (or do it manually from A.log and B.log)
    # SHCH says that S should be < 2, so inequality violated if you received S > 2
    # if not - just try to measure A and B again 
    java -jar bell.jar analize A.log B.log
    
    # you can also choose your polarization states instead of pseudorandom - i've used http://qrng.anu.edu.au/
    # 5 - is a number of tacts with continuous (same in current period of time) detector's setting
    # 101010100 means aa'aa'aa'aa'a'
    java -jar bell.jar measureA e.txt 5 1010110110101011011010110 > myA.log
    
    # you can also simulate man-in-the-middle by using different entanglement file for measureB (measuring leads to corruption)
    # statistic will be much different and always <= 2 

Results:

- usually received S about 1.5..2.5 for true- and pseudo-randomly generated settings and statistically significant number of measurements for each S (about 1000); so it violates inequality sometimes which is enough for "always <= 2" formulation
- if optional1 assumption is true then increasing number of measurements does not affect measured S range; 
- if not - S still may be > 2 but this makes it harder to receive big violations; so even such dynamic S is a legal mathematical expectation of current experiment, despite the fact that high order expectation for S itself seems to be 2.
- increasing count of setting switches affects S by making it harder (but still possible) to reach value more than 2 (that's the trick if you're still reading).
 

Pros:

- it's simpler than "spooky correlation" and fine with no-communication theorem
- it may solve relativity of simultaneity paradox and allow deterministic hidden parameters, so should be fine with special relativity theory
- it seems to be fine with existing quantum predictions (S < 2*sqrt(2), sometimes up to 2.6)

Cons

- should try to model quantum teleportation - it seems to be possible (assuming non-communication principle)
- may not correlate with experiment

How to disprove:

- may be partially disproven by experiment - just greater value of setting switching frequency should be used - it should a little bit decrease the experimental maximum of S. On the other hand, it seems that particle strategy may be improved to compensate it.  

Links: 

- http://en.wikipedia.org/wiki/CHSH_inequality
- http://en.wikipedia.org/wiki/Fr%C3%A9chet_inequalities
- http://en.wikipedia.org/wiki/Loopholes_in_Bell_test_experiments
- http://plato.stanford.edu/entries/bell-theorem/
- http://qrng.anu.edu.au/ - randomizer

