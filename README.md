#Bell's game

This is a simulation of CHSH bell's inequality violation, assuming:

- local realism, determinism - entangled particles are sharing some hidden parameter
- particle knows about local measurement

It's not correlating with practical experiments, but at least shows that it's logically possible to set up an experiment which violate SHCH (sometimes) with local reality assumption and without any communication between participants or particles.
 
## Game rules

"I want to play a game..."(c) Billy The Puppet 

1. Just pick up some statistically significant N (like 100 or 1000). 
2. Randomly choose detector setting (a/a', b/b') and some hidden parameter (H) for every sub-experiment (i.e. unique measurement). Encode detector setting as binary a->0, a'->1, b->0, b'->1. So you will have an array of detector settings, like A = 0010011101110... , B= 101101110110101... and array of hidden parameters, like H=01110110101100... 
3. Now you can built measurement statistic with simple rules: 

- at the detector A - you should always repeat hidden parameter, regardless which setting was used -  a or a' 
- at the detector B - hidden parameter is repeated only for B=0 setting. If B=1 (b') - you may choose strategy (use only one in same statistic):
  - always return 0
  - always return 1
  - randomly return hidden parameter or inverted value of hidden parameter
 
Calculate [S](http://en.wikipedia.org/wiki/CHSH_inequality) based on received statistic. You will get S > 2  with 50% probability. So CHSH will be violated without any non-local magic!

## MatLab simulation

    function S = S(n)
      AA_ = arrayfun(@(x) round(rand), 1:n); # randomly choose detector setting a or a’ (0 or 1 respectively)
      BB_ = arrayfun(@(x) round(rand), 1:n); # randomly choose detector setting b or b’ (0 or 1 respectively)
      notbb = arrayfun(@(x) mod(x + 1, 2), BB_); # invert BB_
      H = arrayfun(@(x) round(rand), 1:n); #randomly choose hidden parameter (0 or 1)
      A = H; #A always correlate with hidden parameter
      H2 =  arrayfun(@(x) round(rand), 1:n).*notbb; # second hidden parameter
      B = abs(H.-H2); # xor
      #alternative - B = H.*BB_;
      PLAN = AA_.+(notbb.*2);
      ABQC = (A(PLAN == 0) - B(PLAN == 0)); # choose only AB combos
      AB = (size(find(ABQC == 0))(2) - size(find(ABQC != 0))(2)) / size(ABQC)(2); #calculate E(AB)
      AB_QC = (A(PLAN == 2) - B(PLAN == 2));
      AB_ = (size(find(AB_QC== 0))(2) - size(find(AB_QC != 0))(2)) / size(AB_QC)(2);
      A_BQC = (A(PLAN == 1) - B(PLAN == 1));
      A_B = (size(find(A_BQC== 0))(2) - size(find(A_BQC != 0)))(2)/ size(A_BQC)(2);
      A_B_QC = (A(PLAN == 3) - B(PLAN == 3));
      A_B_ = (size(find(A_B_QC== 0))(2) - size(find(A_B_QC != 0))(2)) / size(A_B_QC)(2);
      S = AB - AB_ + A_B + A_B_; # calculate S
    end
    S(100)
    
    #graphics
    X = @arrayfun(@S,meshgrid([1:15].*100))
    surf(X)
\* checked with GNU Octave

## Scala simulation

This section describes how to run simulator

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

 
