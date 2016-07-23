using System;
using System.Threading;
using TrocaMensagens.Data;

namespace TrocaMensagens.Threads
{
    public delegate void UsersUpdatedHandler(UserList users);

    public class UsersThread
    {
        public event UsersUpdatedHandler UsersUpdated;

        public void Monitor()
        {
            while (true)
            {
                using (var socket = new SocketWrapper(Configuration.SERVER, 1012))
                {
                    var request = String.Format("GET USERS {0}:{1}", Configuration.UserId, Configuration.Password);
                    var returnValue = socket.SendSync(request);

                    var users = new UserList(returnValue);

                    UsersUpdated(users);
                }

                Thread.Sleep(6000);
            }
        }
    }
}
