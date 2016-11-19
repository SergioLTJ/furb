using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using WebSocketSharp.Server;

using HammerHockey3D;
using UnityEngine;

namespace Assets.Classes
{
    
    class Servidor : WebSocketBehavior
    {
        public HammerHockey3D.GameLoop gameLoop;
    
        protected override void OnMessage(WebSocketSharp.MessageEventArgs e)
        {
            Debug.Log("OnMessage");

            if (gameLoop != null)
                gameLoop.player.avancarPlayer = true;

            base.OnMessage(e);
        }

        protected override void OnOpen()
        {
            Debug.Log("OnOpen");
            base.OnOpen();
        }
    }
}
