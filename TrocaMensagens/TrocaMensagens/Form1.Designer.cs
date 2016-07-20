namespace TrocaMensagens
{
    partial class frmTrocaMensagens
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.btnTest = new System.Windows.Forms.Button();
            this.txbResponse = new System.Windows.Forms.TextBox();
            this.txbRequest = new System.Windows.Forms.TextBox();
            this.lblEnvio = new System.Windows.Forms.Label();
            this.label1 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // btnTest
            // 
            this.btnTest.Location = new System.Drawing.Point(188, 134);
            this.btnTest.Name = "btnTest";
            this.btnTest.Size = new System.Drawing.Size(75, 23);
            this.btnTest.TabIndex = 0;
            this.btnTest.Text = "Teste";
            this.btnTest.UseVisualStyleBackColor = true;
            this.btnTest.Click += new System.EventHandler(this.btnTest_Click);
            // 
            // txbResponse
            // 
            this.txbResponse.Location = new System.Drawing.Point(65, 108);
            this.txbResponse.Name = "txbResponse";
            this.txbResponse.Size = new System.Drawing.Size(198, 20);
            this.txbResponse.TabIndex = 1;
            // 
            // txbRequest
            // 
            this.txbRequest.Location = new System.Drawing.Point(65, 71);
            this.txbRequest.Name = "txbRequest";
            this.txbRequest.Size = new System.Drawing.Size(198, 20);
            this.txbRequest.TabIndex = 2;
            this.txbRequest.KeyPress += TxbRequest_KeyPress;
            // 
            // lblEnvio
            // 
            this.lblEnvio.AutoSize = true;
            this.lblEnvio.Location = new System.Drawing.Point(13, 78);
            this.lblEnvio.Name = "lblEnvio";
            this.lblEnvio.Size = new System.Drawing.Size(34, 13);
            this.lblEnvio.TabIndex = 3;
            this.lblEnvio.Text = "Envio";
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(12, 111);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(45, 13);
            this.label1.TabIndex = 4;
            this.label1.Text = "Retorno";
            // 
            // frmTrocaMensagens
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(284, 220);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.lblEnvio);
            this.Controls.Add(this.txbRequest);
            this.Controls.Add(this.txbResponse);
            this.Controls.Add(this.btnTest);
            this.Name = "frmTrocaMensagens";
            this.Text = "Troca de mensagens";
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnTest;
        private System.Windows.Forms.TextBox txbResponse;
        private System.Windows.Forms.TextBox txbRequest;
        private System.Windows.Forms.Label lblEnvio;
        private System.Windows.Forms.Label label1;
    }
}

