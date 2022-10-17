<h1 align="center">⚔️ PvP-Practice ⚔️</h1>
<p align="center">
    Easy to use PvP spigot plugin
</p>
<br>

<h3>⭐ Features</h3>
<ul>
    <li>Different Kits</li>
    <li>Customizable NPCs for the Queue</li>
    <li>Fully managed world system</li>
    <li>Easy lobby & arena setup</li>
</ul>
<br>

<h3>🛠️ Installation</h3>
<ol>
    <li>Download the PvP-Library & PvP-Game from this repo</li>
    <li>Download ProtocolLib from <a href="https://www.spigotmc.org/resources/protocollib.1997/">here</a></li>
    <li>Place these 3 Files in the plugins folder</li>
    <li>Read the configuration tutorial</li>
</ol>
<br>

<h3>⚙️ Configuration</h3>
<h4><b>Before first launch</b></h4>
<p>
    In your main server folder, create two folders. One called <code>lobbies</code> and the other one called
    <code>arenas</code>. <br>
</p>
<br>

<h4>General Notes</h4>
<p>
    Most world configuration is done with Signs in the worlds. Those signs need to be placed on the ground. 
    Not on the wall! <br>
</p>
<br>

<h4><b>Plugin</b></h4>

```yml
prefix: "&7[&aDUELS&7] &r" # Prefix of the messages send to the player
lobby: "main" # Name of the world folder in path 'lobbies/'. This world will be used as the lobby. See below to configure the lobby world properly
arenas: # Name of the world folders in path 'arenas/' that should be enabled & used. See below to configure the arena worlds properly
  - "Biomes"
  - "Temple"

kits: # Enable / disable certain kits
  bow: true
  rod: true
```

<br>

<h4><b>Lobby</b></h4>
<p>Place signs in the lobby world(s) with these settings</p>
<table>
    <thead>
        <tr>
            <td><b>❓ Usage</b></td>
            <td><b>🗒️ First line</b></td>
            <td><b>❗ Max amount</b></td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Player spawn</td>
            <td><code>{{lobby_spawn}}</code></td>
            <td><b>1</b></td>
        </tr>
        <tr>
            <td>NPC Location</td>
            <td><code>{{lobby_npc}}</code></td>
            <td><b>♾️</b></td>
        </tr>
    </tbody>
</table>
<br>
<p>
    In the main lobby folder <i>(for example <code>lobbies/main</code>)</i> create a file called <code>config.yml</code>. <br>
    <u>Content example:</u><br>

```yml
npc-skins: # Set the skin of the NPCs via the UUIDs you can add as many UUIDs as you have NPCs
  - "26d45041-3742-41f5-85ca-24ec4387347c"
  - "fdef0011-1c58-40c8-bfef-0bdcb1495938"
npc-name: "&6Queue" # Name above the NPCs
```

</p>
<br>

<h4><b>Arenas</b></h4>
<p>Place signs in the arena world(s) with these settings</p>
<table>
    <thead>
        <tr>
            <td><b>❓ Usage</b></td>
            <td><b>🗒️ First line</b></td>
            <td><b>🗒️ Second line</b></td>
            <td><b>❗ Max amount</b></td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>Player spawn</td>
            <td><code>{{arena}}</code></td>
            <td><code>{{spawn}}</code></td>
            <td>2</td>
        </tr>
    </tbody>
</table>
<br>

<h3>🚧 Roadmap</h3>
<table>
    <thead>
        <tr>
            <td><b>🎉 Feature</b></td>
            <td><b>⏱️ Status</b></td>
            <td><b>📆 Released</b></td>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>More kits</td>
            <td>⏱️ In Progress</td>
            <td>❌ Not yet</td>
        </tr>
        <tr>
            <td>API</td>
            <td>💡 Planned</td>
            <td>❌ Not yet</td>
        </tr>
        <tr>
            <td>Laby-Features</td>
            <td>💡 Planned</td>
            <td>❌ Not yet</td>
        </tr>
        <tr>
            <td>Kit Selector Rework</td>
            <td>💡 Planned</td>
            <td>❌ Not yet</td>
        </tr>
    </tbody>
</table>