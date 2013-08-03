local core = luajava.getCore()
local windowManager = core:getRootManager()
local screen2 = windowManager --core:getManager(0)
local fileOut = core:openPrintStream("traceTracker.lua", true)
local fileIn = core:openScanner("payload.txt")
local prop = core:openProperties("test.txt")
local lang = core:openLang("en_US")
local isSoftware = false
local coreNotify = core:getNotifier()

function bootUplink()

core:getScreen(0):setColor(core:createColor(0,0,0))
core:getScreen(0):fillRect(0,0,core:rootWidth(), core:rootHeight())
--Buttons
core:getScreen(0):setColor(core:createColor(0,0,255))
core:getScreen(0):drawImage(core:getResource("software"), 10, core:rootHeight() - 63, 53, 53)
core:getScreen(0):drawImage(core:getResource("hardware"), 73, core:rootHeight() - 34, 24, 24)
core:getScreen(0):fillRect(5,5,15,15)
windowManager:addWindow(0,0,core:rootWidth(), core:rootHeight(),"root")
windowManager:getWindow("root"):updateStyle("FREE")
windowManager:getWindow("root"):updateNext()
windowManager:getWindow("root"):addMouseRegion("software", "boot", 10, core:rootHeight() - 63, 53, 53)

end
function boot()
core:getScreen(0):drawImage(core:generatePattern(core:getResource("Pattern_Cube"), core:rootWidth(), core:rootHeight()), 0, 0, core:rootWidth(), core:rootHeight(), core:getObserver())
core:getScreen(0):drawImage(core:generateBorder(core:createColor(0,0,0,255), core:rootWidth(), core:rootHeight(), 10), 0, 0, core:rootWidth(), core:rootHeight(), core:getObserver())
windowManager:addWindow(core:rootWidth()/2-250,core:rootHeight()/2-100,500,200,lang:getKey("BootTitle"))
windowManager:windowSurface(lang:getKey("BootTitle")):setColor(core:createColor(255,255,255,255))
windowManager:windowSurface(lang:getKey("BootTitle")):drawImage(core:vertGradient(core:createColor(95,1,1,255), core:createColor(181,10,10,255), 500, 200), 0,20,500,180, core:getObserver())
windowManager:windowSurface(lang:getKey("BootTitle")):drawString(lang:getKey("BootText"),5,35)
windowManager:windowSurface(lang:getKey("BootTitle")):drawString(lang:getKey("BootWait"),5,192)

screen2:addWindow(50,50,500,200,"WINDOWTITLE")
screen2:getWindow("WINDOWTITLE"):updateColors(core:createColor(0,0,0,255), core:createColor(0,0,255,255), core:createColor(0,0,0,255))
screen2:windowSurface("WINDOWTITLE"):drawImage(core:vertGradient(core:createColor(0,0,255,255), core:createColor(0,0,0,255), 500, 200), 0,20,500,180, core:getObserver())
windowManager:getWindow("WINDOWTITLE"):addMouseRegion("INTERNALDRAG", "", 0,0,500,20)

windowManager:getWindow(lang:getKey("BootTitle")):addMouseRegion("INTERNALDRAG", "", 0,0,500,20)
windowManager:getWindow(lang:getKey("BootTitle")):addTextBox("TEST", 5, 160, 200, 16, "copy", "boot")

windowManager:getWindow(lang:getKey("BootTitle")):addTextBox("HARPDARP", 5, 144, 200, 16, "killRate", "boot")

windowManager:getWindow(lang:getKey("BootTitle")):textbox("HARPDARP"):setColors(core:createColor(0,0,0), core:createColor(0,0,255), core:createColor(255,255,255))
windowManager:getWindow(lang:getKey("BootTitle")):addCheckBox("BOX", 5,144-16)
--This DESTROYS FRAMERATE!
--windowManager:getWindow(lang:getKey("BootTitle")):enableUpdates(true)

--screen2:repaint()

coreNotify:pushCodeMessage("Bootpro Industries Operating System", "software", "Booting", "Booting complete!")

while(true) do
core:getRoot():clearRect(0,0,100,100)
core:getRoot():drawString(core:getFPS(), 0, 20)
core:sleep(50)
windowManager:getWindow("WINDOWTITLE"):updateColors(core:createColor(core:randInt(255), core:randInt(255), core:randInt(255)), core:createColor(core:randInt(255), core:randInt(255), core:randInt(255)), core:createColor(core:randInt(255), core:randInt(255), core:randInt(255)))
--core:repaintScreen(0)
end
end
function allocate()
core:allocateResourceFromDisk("software")
core:allocateResourceFromDisk("hardware")
end
function postRender(window)
--Normally, this would draw things like the close and minimize boxes
end
function killRate()
core:executeAsync(windowManager:getWindow(lang:getKey("BootTitle")):textbox("HARPDARP"):getText())
--windowManager:getWindow(lang:getKey("BootTitle")):enableUpdates(true)
end
function copy()
fileOut:println()
while(fileIn:hasNextLine()) do
fileOut:println(fileIn:nextLine())
end
end

function software()

windowManager:getWindow("root"):addCheckBox("CHECK",0,0)

end