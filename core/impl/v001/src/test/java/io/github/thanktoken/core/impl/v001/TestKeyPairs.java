/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package io.github.thanktoken.core.impl.v001;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import net.sf.mmm.crypto.asymmetric.access.rsa.Rsa;
import net.sf.mmm.crypto.asymmetric.key.AsymmetricKeyPair;

/**
 * {@link Rsa} {@link AsymmetricKeyPair key pairs} for testing.
 */
public interface TestKeyPairs {

  /** {@link Rsa} with {@link Rsa#keyLength4096()}. */
  Rsa RSA_4096 = Rsa.keyLength4096();

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_PERSON_1 = RSA_4096.createKeyPair(
      "533067703608298806529291008787643575331095928617004372024286506071674726214414474987381106837423454489965228089004977677105866344068668952218281874449560218456550235312611746514007193614484084110110936986063311252127040016677174662315771979243861585817815226863003370583767640875089473637918183793478476782920232753192036893201360947678888634338025943989898631599334824952776354568670745041183475028199967297009165826146568407072407585069915836033541752564949633006556773498819764210035277650736812173431755539528737228494175677002397121326182917949136312907240361491417492032575164607528245736273973855488716895261938331217226532263528558603990178750449965900824003028475818378286471086838004187807203169702480770404393452020208182948008029757021591029773990192907018686148917499197314301821697160761932929289862042217551858617530348549818697364416499922686252691283761174996479616795063983220533198035442740628986752160904052905038900505133976833547258677262647962505920831419842792322318431345581073373172765165056507774559077609685992972791526543452111383432481478835955363833681644370293660218175100065905391428665024640280146770688044439044004425338014970228246691628356891679188764982584446040258545601767016717245940275160349",
      "468346710617908132504639765109671133368395006939089548516996765485253074376703014629497903958051825831701143374962336003292121764674519100183540142679794274665122946569116443600966388579306247815130197471009131664517371319411503685797979012845591804803237877271949190201158746381244974473523796066778929355334345513660947011772500470991202031908441549886909123205055147791031974244534560621806681601595344873304969227604550236953617479413548893583950045206368919366427194074523429867309020662059991225509261698980189658005319674104674096250386993843344506114086699340461406399982879565764017112389267354304290993319492911872914656021607055261225438939910047075532077576006849171588799569951637246610409575607618978040936070746455673412071631753366946631934278617864689600304546284493071538832061175297459256501685106485506902489120642573690960373603965476701694257482480600925440033014906204003802594467846784170211569205474963616686454170942160691706127186057274884090038086891152661420585876830603032661872338980083358409275681194057144942814444920449231127659686695467301676985990892265727352757847033706308694015242629519322859621460955613895739695070853076650445689113782785697030065516808307773370245626856383405504562704766673");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_PERSON_2 = RSA_4096.createKeyPair(
      "731218449754432327822805304003016171843106015679869898046416376275739873928585840910255339807588548411765730642758645956199380816389449609271747036031905373269335298307144315928885856411010611334734501070589006114251597670146258567315753475779162889672607053875943064746835563203591635965082508000651793518950196007033235034240740046097122489109380311472294048599257982687625542275032503066338539192201537011786387887102840429113013835259046205318346818445947515766190201342989528840190258496014559079423314507331250815796281732080104481704534028491282205065058333766556870784314403155336514829283834703232816083626085562256793687179250933711949042937676436545166626389250963829687958427789657080288056732517437838631654917556592147477117592319288187817981055421257978506255172560955579059113037922554722714499324334356615759965930676836761973880977020821813434684622930499343014407265432353879042378508484231997909509102461286500990291048481656940731032126012366429185711755636589934042963289914547865699577052358360738830599819877849573304566948932099983474677562971215117875264435134587753350607704059141789415683078804722654305200090533686040899381339650036154093408786794361426386482028462045650340342424218770971799124571740011",
      "371505877923513942040617486418182545338662145110283168214161863998250005068894254311133742001819977973763881978301335648619417779930718580953070190255189484012833322058272186512719721087590526353245566354080933466381363926070158406944361864031292044759888262705147874130268150021972179116061953841306479832773619122727400200422874426887039191904955006043806932514602332263452212965679803082226442154549866150135821863632196740286800153693645749706043822783995520583286313595640971387030456644976071130316586098701628979257042477273467490536885569169693815433285736903200392549328115138978591242666949114447458338594930505495236008678428652273792923562097160732875383942100941514288376544615862427854814612185095770064209065914657797292044981747288346617461749646726568081633905019101440085292812139551967819024500235500856191084887136680157473188173558362125838586353863919474317939536262789912503475756827850203226234081710956765330306572191091620243736490527733807347898522638349555998419389151061750903655971647269911195168038808035530085302652765045755989262098403921622126233886167741420189391736368276131773425236120695251443826946572098189226172603265367697647856332533178296845809796441019869416721965164938262692453968112273");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_PSEUDONYM = RSA_4096.createKeyPair(
      "642928397186361667688609959103762278566800707301660245419124668070897270065624257874160024824321891467233657777966415448467671122391353429596231969566296422861058237529098560596810425374705212023259363781413781747982320934136687502605729287038762469990085691037177396960040308879685697298434871179828460433446751948614468357433256489468644233215872450869880684498472033838806776691623386635992648918287305609067181595516986158864300723298812461201873070445603380548309917799281337357356849000240280186595198538772122395128436671941523449750475915576331396037383539954242970251846345477718492400061186704446849057092237565798102760551910630935995157912515667015458433345376780012426019393235119158549220305253681762720434869706449749995306279915291285557644332242936579308884288596785394640007059020532556403661311431339728185683137237836572389395059758446935095038015007372527263818908046740570030578931168698262834681243986217755301438014263742819533766376805154528499646056116374044656910512487939277857843602117605298440220906358784914008306895532398948170003669412023081717541934637603182798616947254960352288626782049800686067924918495691961792249780038869142756169465271975315492549090751729041958902815190710908310120125268689",
      "301407328305442358943266435502143708846703158996234331450282837203568178670006861756346683594037960136095440767356457394124246266450279121273702351846359938599312652239864872908073355649670307371448505314862703975235497938270222726552305211025511361947836989356031529429181659978629832976747684241406375304899797166016615435765741068136085347533827687879002916681127527467895988633496146161774846962257338259807133181876861662037425811719070358087284254945614206691888153477655672505110576142993764872559788820135720104184294189675013602547166059327817962553253458070313884035243101125431786021628696771402795221479474896636462328957319636575844434714875230339835758482442172536657559652665904153727886433254235622692083289988007713292448811949476389992891593409571533902114011908473133780708066201565185634029470250691339496332810270082164132049123648571814363737059232782741925279567213766756658031252474083147514612081068938667948695351707772968940867729290281517700200057144563237952219461086990238359489418993944622461160373848590778975774162296548193613629121628944266680207486662864977373905571265764647179803294202027080402456635017232886096032578029097425465094477444670822268435127819896292822880456622170718046786256119185");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_COUNTRY_DE = RSA_4096.createKeyPair(
      "800548682861903016204805050315498428409112375332638270047071156623956385320863911280781748346961827509356215085606147294133269363246920136129552861172280989950938329596882600837945987563187641659246348610871411323377761065194789953203571067410607863301377728915337505718138128512915318045500590057447683895227378471501186047385568335535735950595188867245832170811063541559286784171120288828937930094368090830013550930744884613866196238823687787972415881514025449564255259793454174913636073546306310657217326776631964410601275378965308111324249842055714379497345968366986403487369799870366259456191526393706206562068699002337011370371076403289739171428342908654036253424125256677186143057301496623127982099884069210677191681820691419345023858042029740297548397178222397036668506099821647875966232910058759716561865588000537125823274385704521025677147995459728208642506486076156447491013973466206279151907657195241940494944068478367068073270564351208440046935077573434457012674378672685155199152872339761281037109659340669662406809918780636678201643259029118383086129248401948375952883637171044453233263875059725391930187751926250888687704263802735399172650668524834770017158515236878944908558593941679334360351123381680793467907138579",
      "163060933022926642405327412250508697694939363997671365287674944989444036004824943950549392841960929481398234816634213653804492313196867981402770971264915680224225639894850021187813616552191766918065817898385987606325735576228470193712169769425884681435068607096614742265154429978773922831215761732713873572445950763935934396547750914919305723553033800583862759176600808951812247766914639601713420340108940666949217867380463931984983346690532192221856044712616166840612836458532123547341622988077619374753102753273114315838631994656574118102253858760726478964708047846726299649860375947472713085443347816189086338969655424551458471287353173850284106654576196163915985131660377150880244818097036353117080396843741595790306535425161437171595920520887326754754251566985973944307036346632472715013181838417769650507728649601033574285090226077961613120978589256591694603276347711685120207408137264521959987411757084383042404134492697635252756270559421742678481297950810143305797617580189893526576006578742277930835108878409558089478500744109432516744353927670683047039802240166552324209233705320391630281626937568773155948032845879856438540569466149747977714707654883865203527563967350585491087218393263722632643871710394757694294368518553");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_ORGANIZATION_1 = RSA_4096.createKeyPair(
      "542238736846460441278918177639247966505231068843641557791499661303564560010915186141668660644625681917698650005707145750235855448737019851761400712524131594971327532539349744447292167866851576453176414883150935758423785665290040561739450406578207838038798733672612101109410527290468753543131062357522822126780392566407763737256666677048413664775694313684184526743978292896528819941191269569709329370681067681452109411262119426999166497314044051304200868497712606015786916791888190691391838288698856374741355389447292886648423084021195106072747578204975773692101753026852660770207424198657482679182841049567539438361460760283721120340914391840200144535906540839742874009502644707573227751738436291630648965706119837194879049672101409470843033754104663699317489732312597663106212358216839210879573233256777508752006803291303475290205709649758507935147883405691818076788853006826077756039567227347303773977155975916599170697935467249714601772588233197705600262822292238334223585699492922956784498677242783424656331401199611921077053749997183764965222953632127453722325041638458307863572250792178302671053451747084690784755850150651689992842459569828630109333148403908631276135989474709746042959135870276775293824621664630225925834016103",
      "387196368569520358438299172637769906106654310690827130041451411411633341156763490563465065243254843254718119885974075817012337508430276866849718945705545117742163752807978536409118190820954881591402564409443482179878790935872000827140131530693302568084283734965147948604882644248887753914757273848472664752568302048045386993242557443162055978823762797988807352832212279337958034630939301971760336846195776513367957279525224910275218483319386507056075075819093863562976580130716748523364744315951738416850724774026189976797425934754778042540739447408890493267054913074303840864308208151864944602596984839673187827279818398721900220719757057925522611733803127776076367172245784235714367942624078434188623821747403275605207720165583107398585249847571788360107370127776700278175488101426112793968972084343673556767060514505876578511806828954124366119136751370000096091812473115841082877843080963239830997562205191830266888976254049211430956618962970020959703417860149850969823657463394390674986793661720909722690412325494625276271360230704171906723959694288531960545696475350054325457953022663443036992988518802906456117198531071343873240686909314806116556697547435665938609565438807657029943905731036999306585786494438193998893895235793");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_SUSTAINANILITY_1 = RSA_4096.createKeyPair(
      "596404356678732066088623899223304276165111585663651888029676981852290157900085313522816162800080874009152357235840630716437522320551191339261832879365134071099521396641391021724610328375538298648163065939343401918323524525906428761316859249183813087885500577734529463613732831393448930657798978679683190211873929446051107187436317516273455674484588049332897529331218752200015059981223435047372212147075631157594079070086478228397000345532575449023310928499283551660553904377062197903647327127387971190724425153003767423291517081228967809320110767969433763089222498816637733947055581963850206272898935054590839825205175405036718426758387425560972812859204520616298834691399170856583735895579162226921436806405883125008609677335713203767342835168514553609259791083541641773333463546981355407905852439623941668321444125511003715989894940982843677504802719674552080045199210371294735746234514373003766362528765954888735738582224148776049307079904658086299925783983028014740517822692496503071611330804932144308903026683856820341151415033613874285759057670494667643439905736449346307440255832855867392900315861690055074515329453933566290159758164301884196138189248430187963968461511285384373269454757109753892013128862660861156233557219897",
      "442819109754598201563581472087614417477063792337050839549019362145542809152358993484905236459586727028783034058562416507649874668019911966804717761110631000804167282002076492929483171014140006595047298298799913596069742335331291080239839648828666934045019731030749098973774197409176876662167909769342265219796228189341087870068071628879355983954408265262962811499719310954922148079502149158568928133370465723614567153675145804565330070244520215290207207848621963529037841020917139173161404062112984697814219874958623721216491770642561813960306238756620640430925535078163360145623458784517921742515864012029697207600003936128816946678413728217068340149168285766666101896617735307888499379638438614255538353252444263267300470733050764844079279850715796992028580952841345332698205092748926914268962102963290260438822789337937470735423223995211835603489828301746785721125143397035495821745760788137725158438464770091966894300065327162809549226945563054068644118906646906240120110312665846423145307898158457928026121780807757788916952126341384225968333923064641640076157443780463814641185064462486344739226772711403180359174324675740403925471521366567047705230687340040963332291205634360218066024859233286030534088974664988923260625427153");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_STATE = RSA_4096.createKeyPair(
      "672400640707300828165137935196262776276936035316497398575118465177376450661208246366966660002827347667837107313513768996846504459636484132017099779468540308481855234312208208583490882367158734039193443738931985203536896998822524162708921202378330555269629709441246714216880265514356439163987013181230689741060372434070416175005185598143572027642752240869597588923649656904930849402953235997543908004568133285091278943407661109984768448742671231512777929945449626008906503461276497181771620932829346073058839102505653616829954973932090262054565027538351855476027433764828300340876927586944220832733594014610283410372542068553478200649949135118508475651636600354668495017174638001482469440732419813729992388409386434593428574010999992385916385664459023605595988358934133873824636170218239952160433239923723713017700212071653404440298278967367361901884975484727206217506877137244213549833940583527414853098051400061735330858959082517871340169964279999654132252584930380378162331695753037594923752459404273620944271238454630711350664634986555115323162896167209821684301733502207738787646803136798927477167611167358303948583571419868597808342707372854831203579710592557892305146166472823066219519084306149766852676481890369137527359471077",
      "273322750025825015827994485460555722111442024823099786350323571605738874919733702842913038779244099392269718461815566871782212777586949925644071869707827850190833017106019907482249677377071099910037281859181044079256342768949327001458194009969310862449958580031353471576936543834817821067925203032607314565845985041177287439189132006874662538968871319968354971526405341409392523736128815889872434033320034037487704213686621175366498794185036873941443826445317576893621454326691882218020293599807342102724987010249944494748767879297967325039803657988948127468168680829074048569219850632714253673253626875646092284546487080858424739874415658050831154056272517042752318497698554376166914717444840077263098290629798958586165607418380977334669861941166855757474246506292956737207256902923433174966134556718879694619050183200739433275421810667604249130128125763768052115884599760023380206109427358467263798599484150688989181084265511213496704484461068753192713249122417924448169913078812491826079338716203439726280297011543141976571073949079844686369989087883937016005393773575562862703396693100446014307850442775016321490511217043387759526998554671635119533775683524536860063143948523959498093408552357225032615119238469840815488077788673");

  /** Test {@link AsymmetricKeyPair}. */
  AsymmetricKeyPair KEY_COMMUNITY = RSA_4096.createKeyPair(
      "565341743629140544145853873836327105632954100380788986042243607430014618892715147264018079860580689496568779986896873552098040716395063195145289670500216361956070961329218239959471881736315618476139281532616262485493937174855658840881636302446621918771123067268153261919469214673765073632521689944359213523803366236093345310455944782775670771004449411907077493965492627417562735465858935917609726016253283634794747127289962776615881614665179969465304579032425760185808705994965919787648401523812523038655343980906686869446904948877623862203644526287564528186886905207688273402338900712384581696079484120448895890439260401135129648240967990433385256073973059814969611303938549562911032333919206416004349851782720484494091218496607615712098401957927584719659641613109590725209701813678612078674849944659740174718237589654897966357439630640683225163379560654902148331898951069391504754925559830084131686881820443114427170471520666074404567240514387742382436306258725552282090091057522734127433161209233221425150055361832420284561909896559379952293557231926891973204670738932354778856950469015218257114762845576455074244738060007842488565961318323340271040623561218400779474198605041474248482235905466470511241462191954017995415398833009",
      "520907678575297631853632787974433504749249527833045817036894006107524951957756639496543872476021568212457121142083931310816672821463177840029959595996552561653406489018816434047830523986895264313290914363314854608069287429098582705425614382097772421485771969117516866415445748161953976171833540884997413184130950051615617893971232763975947253885205032079905725782386099443626399497098703088648868810251778164559189465928127503985941181060646011201792610425440010293120596307612677301324997702319956913075691618948551097829024859894779964646371948127025521447325215647214002436389167316448042331804259091771473018906301835876995542392179350771159986554506355634247899243567334075480511196112768257797685350265157343003260980847657787385265999334101117597946451794628543126065359427861859852065281564886578743327096885497892009115505259549265223679816417361338223660310447311736774348552090529880456232616555845141764472608497460174992505029186864415964566438020102132228410298534607612396627325533536025106075880863915390545511319347250170828263156091597626437083193221623790452556930117854949094737842032945532678275329340090007689988040104825339351408330081717933436198145821873216445462231690697512821261764604942307140023408134473");

  /** A {@link List} with all above {@link AsymmetricKeyPair}s. */
  List<AsymmetricKeyPair> KEYS = Collections.unmodifiableList(
      Arrays.asList(KEY_PERSON_1, KEY_PERSON_2, KEY_PSEUDONYM, KEY_COUNTRY_DE, KEY_ORGANIZATION_1, KEY_SUSTAINANILITY_1, KEY_STATE, KEY_COMMUNITY));

}
