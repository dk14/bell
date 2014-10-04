#Bell's game

This is a simulation of CHSH bell's inequality violation, assuming:

- local realism, determinism - entangled particles are sharing some hidden parameters
- simultaneity is just a special case - it's possible to violate CHSH using sequence numbers instead of timetags
- particle knows about measurement
- no errors in detector - "ideal" measurement

It may not correlate with practical experiments, but at least shows that it's logically possible to set up an experiment which violate SHCH with local reality assumption and without any communication between participants or particles.


Please refer to [this publication](https://www.academia.edu/8631507/Hidden_parameters_may_create_additional_statistical_errors_in_bell_test_experiments) for theoretical explanation.
 
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


Links: 

- http://en.wikipedia.org/wiki/CHSH_inequality
- http://en.wikipedia.org/wiki/Fr%C3%A9chet_inequalities
- http://en.wikipedia.org/wiki/Loopholes_in_Bell_test_experiments
- http://plato.stanford.edu/entries/bell-theorem/
- http://qrng.anu.edu.au/ - randomizer

 
