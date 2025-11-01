import mineflayer from 'mineflayer';

const bot = mineflayer.createBot({
  host: '127.0.0.1',
  port: 25565,
  username: 'TestBuddy',
  version: false
});

bot.once('login', () => console.log('Bot logged in as', bot.username));
bot.on('spawn', () => bot.chat('Hello from TestBuddy'));
bot.on('kicked', (reason) => console.log('Kicked:', reason));
bot.on('error', (err) => console.error('Error:', err));
