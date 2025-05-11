"""import websockets
import asyncio

# Server data
PORT = 7890
print("using port: " + str(PORT))
# A set of connected ws clients
connected = set()

# The main behavior function for this server
async def echo(websocket):
    print("A client just connected")
    # print("path: " + path)
    # Send a greeting message to the client when they connect
    await websocket.send("Welcome to the WebSocket server!")
    await websocket.send("sending you a message")
    # Store copy of connected client
    connected.add(websocket)

    # Handle incoming messages
    try:
        async for message in websocket:
            print("Received message from client: " + message)
            # Send response to all connected clients except sender
            for conn in connected:
                if conn != websocket:
                    await conn.send("Someone said: " + message)
    # Handle disconnecting clients
    except websockets.exception.ConnectionClosed as e:
        print("A client just disconnected")
    finally:
        connected.remove(websocket)

# start_server = websockets.serve(echo, "localhost", PORT)

# asyncio.get_event_loop().run_until_complete(start_server)
# asyncio.get_event_loop().run_forever()

# Main function to start the server
async def main():
    start_server = await websockets.serve(echo, "0.0.0.0", 7890)
    print(f"WebSocket server is running on ws://0.0.0.0: {PORT}")
    await start_server.wait_closed()  # Keep the server running

# Run the server using asyncio.run()
if __name__ == "__main__":
    asyncio.run(main())"""


"""Echo server using the asyncio API."""

import asyncio
from websockets.asyncio.server import serve
import websockets
import aiofiles

# Keep track of connected clients
connected_clients = set()

RED = '\033[31m'
GREEN = '\033[32m'
YELLOW = '\033[33m'
BLUE = '\033[34m'
MAGENTA = '\03[35m'
CYAN = '\033[36m'
WHITE = '\033[37m'
RESET = '\033[0m'

async def echo(websocket):
    try:
        connected_clients.add(websocket)
        async for message in websocket:
            print(f"{GREEN}Received message from client!{RESET}")
            # Append mode (adds to the end)
            async with aiofiles.open("data.txt", "a") as f:
                await f.write(f"{message}\n")
    except websockets.exceptions.ConnectionClosedError as e:
        print(f"{RED}Client dc'd: {e}{RESET}")
        connected_clients.remove(websocket)
    except Exception as e:
        print(f"{RED}Unexpected error: {e}{RESET}")

async def send_periodic_messages():
    while True:
        print(f"len conn clients: {len(connected_clients)}")
        if len(connected_clients) > 0:
            await asyncio.gather(*[client.send("EXECUTE_VIBRATION") for client in connected_clients])
        await asyncio.sleep(10)

async def main():
    print(f"WebSocket server is running")
    # Create the periodic hello message task
    sendMsgs = asyncio.create_task(send_periodic_messages())
    async with serve(echo, "0.0.0.0", 8765) as server:
        sendMsgs
        await server.serve_forever()
        # # Start periodic message task
        # await asyncio.gather(server.wait_closed(), send_periodic_messages())


if __name__ == "__main__":
    try:
        asyncio.run(main())
    except KeyboardInterrupt:
        print(f"{BLUE}Server stopped by keyboard interrupt{RESET}")

