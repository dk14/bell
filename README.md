#Bell's game

This is a simulation of CHSH bell's inequality violation, assuming:

- local realism, determinism - entangled particles are sharing some hidden parameters (see /src/main/scala/Model.scala for algorithm)
- simultaneity is just a special case - it's possible to violate CHSH using sequence numbers instead of timetags
- particle knows about measurement

It may not correlate with practical experiments, but at least shows that it's logically possible to set up an experiment which violate SHCH with local reality assumption and without any communication between participants or particles.

Note, that there is also no communication between concrete particles of same detector.

## How it works:

 The measured state of particle is calculated using boolean formula:
 
     out = special and (not hiddenParameter1) or (not special) and hiddenParameter1,
     special = isB' and hiddenParameter2
 
 where 
 
       isB' - true only if B' (67.5°) is measuring now,
       hiddenParameter1 - random (true or false) correlated with entangled particle (at the entanglement moment!),
       hiddenParameter2 - single random (true or false)
       
       
**Theoretical explanation**:

It seems that we have non-standrard mathematical expectation here. So expectation of S calculated by experiments (both real and simulated) is wrong. If we believe in law of large numbers - it should be S = 2.0 with error 2*(sqrt(2) - 1) for such distributions.       
       
## How to use

Actions:

1. Generating e.txt file with hidden parameters (you can make it read-only to ensure there is no tricks)
2. Measuring A statistic using e.txt -> and saving to A.log
3. Measuring B statistic using e.txt -> and saving to B.log (may be done on another machine)
4. Analyzing results from A.log and B.log

Commands:

    # please, install Git and Java 8 before, 
    # MacOS, Linux or Git Cygwin Terminal is also preferred
    git clone https://github.com/dk14/bell.git # or just download jar-file from WebUI
    cd bell
    # 1. Save 1000 particle entanglements (hidden parameters) to the e.txt file:
    # (you may also copy it into another directory or computer to ensure non-communication)
    java -jar bell.jar entangle 1000 > e.txt 
    
    # 2. Measure A particles 1000 times using random settings for detector
    # * results will be saved into A.log
    java -jar bell.jar measureA e.txt 1000 random > A.log
    
    # 3. Do same for B -- you may run it on another computer
    java -jar bell.jar measureB e.txt 1000 random > B.log
    
    # 4. Use 'analize' command to analise correlations (or do it manually from A.log and B.log)
    # * please move A.log and B.log to the same folder if these files were separated  
    # SHCH says that S should be <= 2, so inequality violated only if you received S > 2
    # if still not violated - just try to measure A and B again :-)
    java -jar bell.jar analize A.log B.log
    
    # You can also choose your own polarization states instead of pseudorandom - i used http://qrng.anu.edu.au/
    # 6 - is a number of tacts with continuous (same in current period of time) detector's setting
    # 101010100 means aa'aa'aa'aa'a' or bb'bb'bb'bb'b' depending what you're measuring
    # result will be saved to myA.log
    java -jar bell.jar measureA e.txt 6 1010110110101011011010110 > myA.log
    
    # you can also simulate man-in-the-middle by using different entanglement file for measureB (cause measuring leads to corruption)
    # statistic will be much different and always <= 2 


## Results:

- usually received S about 1.5..2.5 for randomly generated settings and statistically significant number of measurements (1000 for each S); so it violates inequality sometimes, which is enough for "always <= 2" formulation
- increasing number of measurements make it harder but still possible to receive violations; so even such dynamic S is a legal mathematical expectation of current experiment, despite the fact that high order expectation for S itself seems to be 2.


Pros:

- it's simpler than "spooky correlation" and fine with no-communication theorem
- it solves relativity of simultaneity paradox and allow deterministic hidden parameters, so should be fine with special relativity theory
- it seems to be fine with existing quantum predictions (S < 2*sqrt(2), sometimes up to 2.6)

Cons:

- didn't try to model quantum teleportation - it seems to be possible (assuming non-communication principle)
- results may not correlate with real experiment

How to disprove:

- may be partially disproven by real experiment - just greater value of setting switching frequency should be used - it should a little bit decrease the experimental maximum of S. On the other hand, it seems that particle strategy may be improved to compensate it.  

Links: 

- http://en.wikipedia.org/wiki/CHSH_inequality
- http://en.wikipedia.org/wiki/Fr%C3%A9chet_inequalities
- http://en.wikipedia.org/wiki/Loopholes_in_Bell_test_experiments
- http://plato.stanford.edu/entries/bell-theorem/
- http://qrng.anu.edu.au/ - randomizer

 
refer to this publication or to the author if you're publishing research based on that! :-)

