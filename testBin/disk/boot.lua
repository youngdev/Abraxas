local core = luajava.getCore()
local root = core:getRoot()
local windowMan = core:getWindowManager()
function boot()
windowMan:addWindow("Bitpro Industries Bootloader", 0, 0, 500, 200)
windowMan:getWindow("Bitpro Industries Bootloader"):setHandle(0, 0, 500, 20)
windowargs = {core:getGraphics(), "Bitpro Industries Bootloader", core:createBounds(0, 0, 500, 200), core:createColor(93, 6, 6), core:createColor(184, 11, 11), true}
windowMan:getWindow("Bitpro Industries Bootloader"):addActionToQueue("gradient", "drawGradient", windowargs)

stringargs = {core:getGraphics(), "Bitpro Industries Bootloader", "Now starting Bitpro Industries Unified Operating\nSystem.", 5, 25, windowMan:getWindow("Bitpro Industries Bootloader"):getColors()[4]}
windowMan:getWindow("Bitpro Industries Bootloader"):addActionToQueue("text", "drawString", stringargs)

stringargs = {core:getGraphics(), "Bitpro Industries Bootloader", "Please Wait...", 5, 175, windowMan:getWindow("Bitpro Industries Bootloader"):getColors()[4]}
windowMan:getWindow("Bitpro Industries Bootloader"):addActionToQueue("pleaseWait", "drawString", stringargs)

windowMan:getWindow("Bitpro Industries Bootloader"):addHotSpot("boot", "floop", core:createBounds(0,20, 500, 90))
end

function floop()

windowMan:addWindow("TestWindow", 0, 0, 500, 200)
windowMan:getWindow("TestWindow"):setHandle(0, 0, 500, 20)
windowargs = {core:getGraphics(), "TestWindow", core:createBounds(0, 0, 500, 200), core:getDefaultColor("blue"), core:getDefaultColor("black"), true}
windowMan:getWindow("TestWindow"):addActionToQueue("gradient", "drawGradient", windowargs)
windowMan:getWindow("TestWindow"):setColors(core:getDefaultColor("black"), core:getDefaultColor("black"), core:getDefaultColor("blue"), nil)
windowMan:getWindow("TestWindow"):addTextBox("TestText", core:createBounds(0,20, 500, 20))
end

function flarp()

windowMan:addWindow("FlarpWindow", 0, 0, 500, 200)
windowMan:getWindow("FlarpWindow"):setHandle(0, 0, 500, 20)
windowargs = {core:getGraphics(), "FlarpWindow", core:createBounds(0, 0, 500, 200), core:getDefaultColor("blue"), core:getDefaultColor("black"), true}
windowMan:getWindow("FlarpWindow"):addActionToQueue("gradient", "drawGradient", windowargs)
windowMan:getWindow("FlarpWindow"):setColors(core:getDefaultColor("black"), core:getDefaultColor("black"), core:getDefaultColor("blue"), nil)
windowMan:getWindow("FlarpWindow"):addTextBox("TestText", core:createBounds(0,20, 500, 20))
end